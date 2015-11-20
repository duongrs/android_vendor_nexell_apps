LOCAL_PATH := $(call my-dir)

####################################################################################################
#
#	Add Prebuilt Library
#
include $(CLEAR_VARS)
LOCAL_MODULE := libtheoraparser_and
LOCAL_SRC_FILES := libs/libtheoraparser_and.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavcodec-2.1.4
LOCAL_SRC_FILES := libs/libavcodec-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavdevice-2.1.4
LOCAL_SRC_FILES := libs/libavdevice-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavfilter-2.1.4
LOCAL_SRC_FILES := libs/libavfilter-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavformat-2.1.4
LOCAL_SRC_FILES := libs/libavformat-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavresample-2.1.4
LOCAL_SRC_FILES := libs/libavresample-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavutil-2.1.4
LOCAL_SRC_FILES := libs/libavutil-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libswresample-2.1.4
LOCAL_SRC_FILES := libs/libswresample-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libswscale-2.1.4
LOCAL_SRC_FILES := libs/libswscale-2.1.4.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libNX_MPMANAGER_LOLLIPOP
LOCAL_SRC_FILES := libs/libNX_MPMANAGER_LOLLIPOP.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libNX_FILTERHELPER_LOLLIPOP
LOCAL_SRC_FILES := libs/libNX_FILTERHELPER_LOLLIPOP.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libNX_FILTER_LOLLIPOP
LOCAL_SRC_FILES := libs/libNX_FILTER_LOLLIPOP.so
include $(PREBUILT_SHARED_LIBRARY)

####################################################################################################
#
#	Build JNI library
#
include $(CLEAR_VARS)
LOCAL_MODULE    		:= libnxmovieplayer_lollipop
LOCAL_SRC_FILES 		:= nxmovieplayer.cpp

LOCAL_C_INCLUDES		:=	\
	$(JNI_H_INCLUDE)		\
	$(LOCAL_PATH)/include

LOCAL_LDFLAGS	+= \
	-L$(LOCAL_PATH)/libs	\
	-ltheoraparser_and		\
	-lavcodec-2.1.4			\
	-lavdevice-2.1.4		\
	-lavfilter-2.1.4		\
	-lavformat-2.1.4		\
	-lavresample-2.1.4		\
	-lavutil-2.1.4			\
	-lswresample-2.1.4		\
	-lswscale-2.1.4			\
	-lNX_MPMANAGER_LOLLIPOP	\
	-lNX_FILTERHELPER_LOLLIPOP\
	-lNX_FILTER_LOLLIPOP

LOCAL_SHARED_LIBRARIES	:=	\
	liblog					\
	libandroid

LOCAL_LDLIBS	:= 			\
	-llog					\
	-landroid

LOCAL_MODULE_TAGS   	:= optional
LOCAL_PRELINK_MODULE    := false

include $(BUILD_SHARED_LIBRARY)
