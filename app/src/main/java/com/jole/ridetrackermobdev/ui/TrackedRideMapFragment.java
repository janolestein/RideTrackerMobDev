package com.jole.ridetrackermobdev.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jole.ridetrackermobdev.model.RideDao;
import com.jole.ridetrackermobdev.model.Ride;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.milestones.MilestoneBitmapDisplayer;
import org.osmdroid.views.overlay.milestones.MilestoneDisplayer;
import org.osmdroid.views.overlay.milestones.MilestoneLineDisplayer;
import org.osmdroid.views.overlay.milestones.MilestoneLister;
import org.osmdroid.views.overlay.milestones.MilestoneManager;
import org.osmdroid.views.overlay.milestones.MilestoneMeterDistanceLister;
import org.osmdroid.views.overlay.milestones.MilestoneMeterDistanceSliceLister;
import org.osmdroid.views.overlay.milestones.MilestonePathDisplayer;
import org.osmdroid.views.overlay.milestones.MilestoneVertexLister;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackedRideMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackedRideMapFragment extends Fragment
{

    private static final String PREFS_NAME = "org.andnav.osm.prefs";
    private static final String PREFS_TILE_SOURCE = "tilesource";
    private static final String PREFS_LATITUDE_STRING = "latitudeString";
    private static final String PREFS_LONGITUDE_STRING = "longitudeString";
    private static final String PREFS_ORIENTATION = "orientation";
    private static final String PREFS_ZOOM_LEVEL_DOUBLE = "zoomLevelDouble";

    private static final int MENU_ABOUT = Menu.FIRST + 1;
    private static final int MENU_LAST_ID = MENU_ABOUT + 1; // Always set to last unused id

    // ===========================================================
    // Fields
    // ===========================================================
    private SharedPreferences mPrefs;
    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay = null;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private CopyrightOverlay mCopyrightOverlay;

    //for Ride Overlay
    private static final float LINE_WIDTH_BIG = 12;
    private static final float TEXT_SIZE = 20;
    private static final int COLOR_POLYLINE_STATIC = Color.BLUE;
    private static final int COLOR_POLYLINE_ANIMATED = Color.GREEN;
    private static final int COLOR_BACKGROUND = Color.WHITE;
    private double mAnimatedMetersSoFar;
    private boolean mAnimationEnded;

    //private final List<GeoPoint> mGeoPoints = Model.getInstance().findRideById(1).get().getGeoPoints();
    //private final List<GeoPoint> mGeoPoints = getGeoPoints();
    private  List<GeoPoint> mGeoPoints;
    private double rideLength;

    public static TrackedRideMapFragment newInstance(int rideId)
    {
        final TrackedRideMapFragment fragment = new TrackedRideMapFragment();
        final Bundle args = new Bundle();
        args.putInt("rideId", rideId);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        int rideId = -1;
        Optional<Ride> ride;
        Bundle args = this.getArguments();
        Log.v("ABC", Integer.toString(rideId));
        if (args != null) {
            rideId = args.getInt("rideId", -1);

        }
        Log.v("ABC", Integer.toString(rideId));
        ride = RideDao.getInstance().findRideById(rideId);
        if (ride.isPresent())
        {
            mGeoPoints = ride.get().getGeoPoints();
            rideLength = ride.get().getRideLengthKm();

        }
        else {
            Toast.makeText(getActivity(), "Abort", Toast.LENGTH_SHORT).show();
        }
        //Log.v("ABC", ride.toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        mMapView = new MapView(inflater.getContext());
        mMapView.setDestroyMode(false);
        mMapView.setTag("mapView");
        return mMapView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        //needed for pinch zooms
        mMapView.setMultiTouchControls(true);

        //scales tiles to the current screen's DPI, helps with readability of labels
        mMapView.setTilesScaledToDpi(true);

        mMapView.post(new Runnable()
        {
            @Override
            public void run()
            {
                final BoundingBox boundingBox = BoundingBox.fromGeoPoints(mGeoPoints);
                mMapView.zoomToBoundingBox(boundingBox, false, 30);
            }
        });

        addOverlays();
    }

    protected void addOverlays()
    {

        final Polyline line = new Polyline(mMapView);
        line.getOutlinePaint().setColor(COLOR_POLYLINE_STATIC);
        line.getOutlinePaint().setStrokeWidth(LINE_WIDTH_BIG);
        line.setPoints(mGeoPoints);
        line.getOutlinePaint().setStrokeCap(Paint.Cap.ROUND);
        final List<MilestoneManager> managers = new ArrayList<>();
        final MilestoneMeterDistanceSliceLister slicerForPath = new MilestoneMeterDistanceSliceLister();
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), org.osmdroid.library.R.drawable.next);
        final MilestoneMeterDistanceSliceLister slicerForIcon = new MilestoneMeterDistanceSliceLister();
        managers.add(getAnimatedPathManager(slicerForPath));
        managers.add(getAnimatedIconManager(slicerForIcon, bitmap));
        managers.add(getHalfKilometerManager());
        managers.add(getKilometerManager());
        managers.add(getStartManager(bitmap));
        line.setMilestoneManagers(managers);
        mMapView.getOverlayManager().add(line);
        final ValueAnimator percentageCompletion = ValueAnimator.ofFloat(0, 10000);
        percentageCompletion.setDuration(8000); // 5 seconds
        percentageCompletion.setStartDelay(1000); // 1 second
        percentageCompletion.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mAnimatedMetersSoFar = (float) animation.getAnimatedValue();
                slicerForPath.setMeterDistanceSlice(0, mAnimatedMetersSoFar);
                slicerForIcon.setMeterDistanceSlice(mAnimatedMetersSoFar, mAnimatedMetersSoFar);
                mMapView.invalidate();
            }
        });
        percentageCompletion.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mAnimationEnded = true;
            }
        });
        percentageCompletion.start();
    }

    /**
     * @since 6.0.2
     */
    private Paint getFillPaint(final int pColor)
    {
        final Paint paint = new Paint();
        paint.setColor(pColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    /**
     * @since 6.0.2
     */
    private Paint getStrokePaint(final int pColor, final float pWidth)
    {
        final Paint paint = new Paint();
        paint.setStrokeWidth(pWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(pColor);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    /**
     * @since 6.0.2
     */
    private Paint getTextPaint(final int pColor)
    {
        final Paint paint = new Paint();
        paint.setColor(pColor);
        paint.setTextSize(TEXT_SIZE);
        paint.setAntiAlias(true);
        return paint;
    }

    /**
     * Kilometer milestones
     *
     * @since 6.0.2
     */
    private MilestoneManager getKilometerManager()
    {
        final float backgroundRadius = 20;
        final Paint backgroundPaint1 = getFillPaint(COLOR_BACKGROUND);
        final Paint backgroundPaint2 = getFillPaint(COLOR_POLYLINE_ANIMATED);
        final Paint textPaint1 = getTextPaint(COLOR_POLYLINE_STATIC);
        final Paint textPaint2 = getTextPaint(COLOR_BACKGROUND);
        final Paint borderPaint = getStrokePaint(COLOR_BACKGROUND, 2);
        return new MilestoneManager(
                new MilestoneMeterDistanceLister(1000),
                new MilestoneDisplayer(0, false)
                {
                    @Override
                    protected void draw(final Canvas pCanvas, final Object pParameter)
                    {
                        final double meters = (double) pParameter;
                        final int kilometers = (int) Math.round(meters / 1000);
                        final boolean checked = meters < mAnimatedMetersSoFar || (kilometers == 10 && mAnimationEnded);
                        final Paint textPaint = checked ? textPaint2 : textPaint1;
                        final Paint backgroundPaint = checked ? backgroundPaint2 : backgroundPaint1;
                        final String text = "" + kilometers + "K";
                        final Rect rect = new Rect();
                        textPaint1.getTextBounds(text, 0, text.length(), rect);
                        pCanvas.drawCircle(0, 0, backgroundRadius, backgroundPaint);
                        pCanvas.drawText(text, -rect.left - rect.width() / 2, rect.height() / 2 - rect.bottom, textPaint);
                        pCanvas.drawCircle(0, 0, backgroundRadius + 1, borderPaint);
                    }
                }
        );
    }

    /**
     * Half-kilometer milestones
     *
     * @since 6.0.2
     */
    private MilestoneManager getHalfKilometerManager()
    {
        final Path arrowPath = new Path(); // a simple arrow towards the right
        arrowPath.moveTo(-5, -5);
        arrowPath.lineTo(5, 0);
        arrowPath.lineTo(-5, 5);
        arrowPath.close();
        final Paint backgroundPaint = getFillPaint(COLOR_BACKGROUND);
        return new MilestoneManager( // display an arrow at 500m every 1km
                new MilestoneMeterDistanceLister(500),
                new MilestonePathDisplayer(0, true, arrowPath, backgroundPaint)
                {
                    @Override
                    protected void draw(final Canvas pCanvas, final Object pParameter)
                    {
                        final int halfKilometers = (int) Math.round(((double) pParameter / 500));
                        if (halfKilometers % 2 == 0)
                        {
                            return;
                        }
                        super.draw(pCanvas, pParameter);
                    }
                }
        );
    }

    /**
     * Animated path
     *
     * @since 6.0.2
     */
    private MilestoneManager getAnimatedPathManager(final MilestoneLister pMilestoneLister)
    {
        final Paint slicePaint = getStrokePaint(COLOR_POLYLINE_ANIMATED, LINE_WIDTH_BIG);
        return new MilestoneManager(pMilestoneLister, new MilestoneLineDisplayer(slicePaint));
    }

    /**
     * Animated icon
     *
     * @since 6.0.2
     */
    private MilestoneManager getAnimatedIconManager(final MilestoneLister pMilestoneLister,
                                                    final Bitmap pBitmap)
    {
        return new MilestoneManager(
                pMilestoneLister,
                new MilestoneBitmapDisplayer(0, true, pBitmap,
                        pBitmap.getWidth() / 2, pBitmap.getHeight() / 2)
        );
    }

    /**
     * Starting point
     *
     * @since 6.0.2
     */
    private MilestoneManager getStartManager(final Bitmap pBitmap)
    {
        return new MilestoneManager(
                new MilestoneVertexLister(),
                new MilestoneBitmapDisplayer(0, true,
                        pBitmap, pBitmap.getWidth() / 2, pBitmap.getHeight() / 2)
                {
                    @Override
                    protected void draw(final Canvas pCanvas, final Object pParameter)
                    {
                        if (0 != (int) pParameter)
                        { // we only draw the start
                            return;
                        }
                        super.draw(pCanvas, pParameter);
                    }
                }
        );
    }


    private List<GeoPoint> getGeoPoints()
    {
        final List<GeoPoint> pts = new ArrayList<>();
        pts.add(new GeoPoint(52.458159970620216, 13.527038899381642)); // saint paul
        pts.add(new GeoPoint(52.46051831693104, 13.521824258809318)); // hôtel de ville
        pts.add(new GeoPoint(52.46187056912226, 13.516528352449457)); // louvre 1
        pts.add(new GeoPoint(52.46330523870933, 13.510321300073972)); // louvre 2
        pts.add(new GeoPoint(52.462761066704125, 13.506884073289987)); // opéra loop 1
        pts.add(new GeoPoint(52.46080964195072, 13.503933924512413)); // opéra loop 2
        pts.add(new GeoPoint(52.46082614030988, 13.501795751994008)); // opéra loop 3
        pts.add(new GeoPoint(52.45821491925012, 13.497420340748592)); // opéra loop 4
        pts.add(new GeoPoint(52.46126039973983, 13.4932430495305)); // opéra loop 5
        pts.add(new GeoPoint(52.46363503504482, 13.489868695841839)); // opéra loop 6
        pts.add(new GeoPoint(52.4661139469194, 13.495570882101445)); // opéra loop 7
        return pts;
    }
}