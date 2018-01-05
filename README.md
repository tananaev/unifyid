# Secure Camera Capture

Just a test app. Do not use in production.

## Build Project

Standard gradle project. Import in Android Studio and build. No special steps needed.

## Further Considerations

- Handle camera permissions better
- Handle case when user presses home button in the middle of the capture process
- Capturing photo takes some time, so total time might be slightly more than 5 seconds
- Video capturing could be a better option, but not enough time to implement both to compare
- Targeting Android API 23 to use crypto; if lower version required might need to use different APIs
- Using ECB mode which is not the most secure one; can be improved by using GCM encryption mode
- File reading logic needs some improvements
