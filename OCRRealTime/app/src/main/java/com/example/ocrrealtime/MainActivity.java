package com.example.ocrrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.otaliastudios.cameraview.size.Size;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CameraView camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);

        final GraphicOverlay mGraphicOverlay = findViewById(R.id.overlay);

        final TextRecognizer recognizer = TextRecognition.getClient();

        camera.addFrameProcessor(new FrameProcessor() {
            @Override
            @WorkerThread
            public void process(@NonNull Frame frame) {
                long time = frame.getTime();
                Size size = frame.getSize();
                int format = frame.getFormat();
                int userRotation = frame.getRotationToUser();
                int viewRotation = frame.getRotationToView();
                InputImage image = null;
                if (frame.getDataClass() == byte[].class) {
                    byte[] data = frame.getData();
                    image = InputImage.fromByteArray(data, size.getWidth(), size.getHeight(), userRotation, InputImage.IMAGE_FORMAT_NV21);
                } else if (frame.getDataClass() == Image.class) {
                    Image data = frame.getData();
                    image = InputImage.fromMediaImage(data, userRotation);
                }
                if(image == null) {
                    Log.v("Image is null", "frame class is neither byte[] or Image");
                    return;
                }
                Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text text) {
                        List<Text.TextBlock> blocks = text.getTextBlocks();
                        if (blocks.size() == 0) {
                            Log.v("Recognizer: ", "Success: no text");
                            mGraphicOverlay.clear();
                            return;
                        }
                        mGraphicOverlay.clear();
                        for (int i = 0; i < blocks.size(); i++) {
                            List<Text.Line> lines = blocks.get(i).getLines();
                            for (int j = 0; j < lines.size(); j++) {
                                List<Text.Element> elements = lines.get(j).getElements();
                                for (int k = 0; k < elements.size(); k++) {
                                    GraphicOverlay.Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                                    mGraphicOverlay.add(textGraphic);

                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("Recognizer: ", "Failed");
                    }
                });
            }
        });
    }
}