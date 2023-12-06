//package com.jole.ridetrackermobdev.hilt;
//
//import com.jole.ridetrackermobdev.model.DaoInterface;
//import com.jole.ridetrackermobdev.model.RideDatabase;
//
//import org.mockito.Mockito;
//
//import java.time.Clock;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import dagger.hilt.components.SingletonComponent;
//import dagger.hilt.testing.TestInstallIn;
//
//@Module
//@TestInstallIn(
//        components = SingletonComponent.class,
//        replaces = ClockModule.class
//)
//public class ClockModuleMock
//{
//    @Singleton
//    @Provides
//    public Clock provideDaoInterface()
//    {
//        return Mockito.mock(Clock.class);
//    }
//}
