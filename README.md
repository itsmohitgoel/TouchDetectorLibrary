# TouchDetectorLibrary
Android Library Module to capture swipe/tab event along any activity of android project.

## Integration:
1. First add touchlib.jar file to your android studio project's lib folder.
2. Right Click on app 'touchlib' and select 'Add as Library' option.
3. add below permission to your AndroidManifest.xml file
  -  "READ_PHONE_STATE"

### How to use library in android app
1. register your Activity's object in onStart() callback method.<br>
  syntax -  **EventTracker.getInstance().register(YOUR_ACTIVITY_CLASS_NAME.this);**<br>
    e.g - EventTracker.getInstance().register(MainActivity.this);
  
2. Unregister your Activity's object in onStop() callback method.<br>
syntax -  **EventTracker.getInstance().unregister(YOUR_ACTIVITY_CLASS_NAME.this);**<br>
   e.g - EventTracker.getInstance().unregister(MainActivity.this);
