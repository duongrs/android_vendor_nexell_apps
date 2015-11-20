#
# Nexell Application
#

EN_APP_PLAYER		:= true
EN_APP_DUAL_AUDIO	:= false

# Nexell Player
ifeq ($(EN_APP_PLAYER),true)
PRODUCT_COPY_FILES	+= \
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libtheoraparser_and.so:system/app/NxPlayerBasedFilter/lib/arm/libtheoraparser_and.so					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavcodec-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavcodec-2.1.4.so						\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavdevice-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavdevice-2.1.4.so						\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavfilter-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavfilter-2.1.4.so 					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavformat-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavformat-2.1.4.so 					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavresample-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavresample-2.1.4.so					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libavutil-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libavutil-2.1.4.so							\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libswresample-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libswresample-2.1.4.so					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libswscale-2.1.4.so:system/app/NxPlayerBasedFilter/lib/arm/libswscale-2.1.4.so						\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_FILTER_KITKAT.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_FILTER_KITKAT.so					\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_FILTERHELPER_KITKAT.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_FILTERHELPER_KITKAT.so		\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_MPMANAGER_KITKAT.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_MPMANAGER_KITKAT.so			\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libnxmovieplayer_kitkat.so:system/app/NxPlayerBasedFilter/lib/arm/libnxmovieplayer_kitkat.so			\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_FILTER_LOLLIPOP.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_FILTER_LOLLIPOP.so				\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_FILTERHELPER_LOLLIPOP.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_FILTERHELPER_LOLLIPOP.so	\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libNX_MPMANAGER_LOLLIPOP.so:system/app/NxPlayerBasedFilter/lib/arm/libNX_MPMANAGER_LOLLIPOP.so		\
	vendor/nexell/apps/app/NxPlayerBasedFilter/lib/arm/libnxmovieplayer_lollipop.so:system/app/NxPlayerBasedFilter/lib/arm/libnxmovieplayer_lollipop.so

PRODUCT_PACKAGES += \
	NxPlayerBasedFilter
endif

# Nexell DualAudio Test Applicatoin
ifeq ($(EN_APP_DUAL_AUDIO),true)
PRODUCT_PACKAGES += \
	NxDualAudioTest
endif
