LOCAL_PATH			:=$(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE		:= NxDualAudioTest
LOCAL_SRC_FILES		:= app/NxDualAudioTest/NxDualAudioTest.apk
LOCAL_MODULE_PATH	:= $(TARGET_OUT_APPS)
LOCAL_MODULE_CLASS	:= APPS
LOCAL_MODULE_OWNER	:= nexell
LOCAL_MODULE_TAGS	:= optional
LOCAL_CERTIFICATE	:= PRESIGNED
include $(BUILD_PREBUILT)

include $(CLEAR_VARS)
LOCAL_MODULE		:= NxPlayerBasedFilter
LOCAL_SRC_FILES		:= app/NxPlayerBasedFilter/NxPlayerBasedFilter.apk
LOCAL_MODULE_PATH	:= $(TARGET_OUT_APPS)
LOCAL_MODULE_CLASS	:= APPS
LOCAL_MODULE_OWNER	:= nexell
LOCAL_MODULE_TAGS	:= optional
LOCAL_CERTIFICATE	:= PRESIGNED
include $(BUILD_PREBUILT)
