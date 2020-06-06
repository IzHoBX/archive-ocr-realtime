# archive-ocr-realtime
Very simple prototype that does OCR (with annotation) on real-time camera feed from android 

## Background
This prototype is very simple and was created back in late-2018 as proof-of-concept for the project [Chameleon](https://drive.google.com/file/d/1zdQa7_9QbrFcsNzgxBMQ7Sv3g_zm-tcT/view?usp=sharing). This repo is created in mid-2020 more as an archive of our work since the project has long been ceased. It has been been updated Android frameworks and libraries to that of 2020. 

## Use Case
Captures the number of items supermarket staff are stocking on to the shelves, as well as the respective expiry date of each item. With the use of computer vision technology, there is no need to educate the staff on extra procedures such as keying-in data or scanning barcodes, as the technology can perform well in more uncertain settings. 
With this information, we can log them in an information system and accurately monitor the expiry date of products without the need of any manual works. From our on-the-ground interview, staff are deployed to check the expiry dates of products manually, everyday. 

## Next steps
1. To build a binary classfier for the separate texts of expiry dates from the rest of the irrelevant product information. LSTM should probably be used as the expiry dates often occur after a fixed set of key words like "Best Before", "Use by", etc.
2. Parse the texts into formatted dates. Possible approaches are pure multi-layer perceptron and syntacting parsing. 

## Frameworks and references
[Google's Firebase ML Kit for OCR (annotation overlay and driver code)](https://developers.google.com/ml-kit/vision/text-recognition/android)

[Camera View Wrapper for Androuid by Natario](https://natario1.github.io/CameraView/home)
