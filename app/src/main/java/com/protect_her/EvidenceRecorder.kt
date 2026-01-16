package com.protect_her.shehelp

    import android.content.Context
    import android.util.Log
    import androidx.camera.core.CameraSelector
    import androidx.camera.lifecycle.ProcessCameraProvider
    import androidx.camera.video.*
    import androidx.core.content.ContextCompat
    import androidx.lifecycle.LifecycleOwner
    import java.text.SimpleDateFormat
    import java.util.*

    class EvidenceRecorder(private val context: Context) {
        private var videoCapture: VideoCapture<Recorder>? = null
        private var recording: Recording? = null

        fun startRecording(lifecycleOwner: LifecycleOwner) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    val recorder = Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.LOWEST)).build()
                    videoCapture = VideoCapture.withOutput(recorder)
                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, videoCapture)
                    captureVideo()
                } catch (e: Exception) { Log.e("EvidenceRecorder", "Error", e) }
            }, ContextCompat.getMainExecutor(context))
        }

        private fun captureVideo() {
            val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".mp4"
            val contentValues = android.content.ContentValues().apply {
                put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                put(android.provider.MediaStore.Video.Media.RELATIVE_PATH, "Movies/SheProtect")
            }
            val options = MediaStoreOutputOptions.Builder(context.contentResolver, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI).setContentValues(contentValues).build()

            recording = videoCapture?.output?.prepareRecording(context, options)?.apply {
                if (androidx.core.content.PermissionChecker.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) == 0) withAudioEnabled()
            }?.start(ContextCompat.getMainExecutor(context)) { }
        }

        fun stopRecording() { recording?.stop(); recording = null }
    }