LOCAL_PATH  := $(call my-dir)

####################################################################################################
#
#	Build Package
#
include $(CLEAR_VARS)

LOCAL_SRC_FILES		:=	\
	$(call all-java-files-under, src)

LOCAL_PACKAGE_NAME	:=	\
	NxPlayerBasedFilter

LOCAL_RESOURCE_DIR :=	\
	$(LOCAL_PATH)/res

$(shell cp $(wildcard $(LOCAL_PATH)/jni/libs/*.so) $(TARGET_OUT_INTERMEDIATE_LIBRARIES))

#$(shell cp $(wildcard $(LOCAL_PATH)/jni/libs/*.so) #out/target/product/drone2_s5p4418/system/lib)

#LOCAL_JNI_SHARED_LIBRARIES :=	\
#	libtheoraparser_and			\
#	libavcodec-2.1.4			\
#	libavdevice-2.1.4			\
#	libavfilter-2.1.4			\
#	libavformat-2.1.4			\
#	libavresample-2.1.4			\
#	libavutil-2.1.4				\
#	libswresample-2.1.4			\
#	libswscale-2.1.4			\
#	libNX_FILTER_LOLLIPOP		\
#	libNX_FILTERHELPER_LOLLIPOP	\
#	libNX_MPMANAGER_LOLLIPOP	\
#	libnxmovieplayer_lollipop

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
	-lNX_MPMANAGER_LOLLIPOP		\
	-lNX_FILTERHELPER_LOLLIPOP	\
	-lNX_FILTER_LOLLIPOP

LOCAL_JNI_SHARED_LIBRARIES :=	\
	libnxmovieplayer_lollipop


include $(BUILD_PACKAGE)

include $(call all-makefiles-under, $(LOCAL_PATH))
