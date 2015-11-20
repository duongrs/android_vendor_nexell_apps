LOCAL_PATH	:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES		:= $(call all-java-files-under, src)
#LOCAL_RESOURCE_DIR	:= $(LOCAL_PATH)/res

LOCAL_MODULE_PATH	:= $(LOCAL_PATH)/../../app
LOCAL_PACKAGE_NAME	:= NxDualAudioTest
LOCAL_CERTIFICATE	:= media

LOCAL_MODULE_TAGS	:= optional

include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))

