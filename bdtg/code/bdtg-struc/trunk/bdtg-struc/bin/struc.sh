#! /usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`

# the root of the itools installation
if [ -z "$STRUC_HOME" ]; then
  export STRUC_HOME=`dirname "$bin"`
fi

STRUC_CONF_DIR="${STRUC_CONF_DIR:-$STRUC_HOME/conf}"

# if no args specified, show usage
if [ $# = 0 ]; then
  echo "Usage: sturc <command>"
  echo "where command is one of:"
  echo "common_crawler     Run the CommonCrawler. "
  echo "zj_atmosphere     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_atmosphere_old     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_atmosphere_gov     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_pollution_gas     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_pollution_water     Get zhejiang atmosphere data with webservice interface. "
  echo "weather_day     Get zhejiang atmosphere data with webservice interface. "
  echo "weather_hour     Get zhejiang atmosphere data with webservice interface. "
  echo "weather_index     Get zhejiang atmosphere data with webservice interface. "
  echo "weather_forecast     Get zhejiang atmosphere data with webservice interface. "
  echo "atmosphere     Get the country atmosphere data with spider. "
  echo "atmosphere_aqi     Get the country atmosphere data with spider. "
  echo "atmosphere_aqi_d     Get the country atmosphere data with spider. "
  echo "traffic     Get HANGZHOU traffic data with spider. "
  echo "traffic_index     Get HANGZHOU traffic data with spider. "
  echo "traffic_indicator     Get HANGZHOU traffic data with spider. "
  echo "disaster_alarm     Get the country disaster alarm data with spider. "
  echo "drink_water     Get the drink water data with spider. "
  echo "surface_water     Get the surface water data with spider. "
  
  echo "weather_f_lifeindex_process     Get the surface water data with spider. "
  echo "hbase_weather_process     Get the surface water data with spider. "
  echo "hbase_atmosphere_process     Get the surface water data with spider. "
  
  echo "weather_forecast_monitor     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_atmosphere_day_monitor     Get zhejiang atmosphere data with webservice interface. "
  echo "zj_atmosphere_hour_monitor     Get zhejiang atmosphere data with webservice interface. "
  exit 1
fi

COMMAND=$1
shift

JAVA=$JAVA_HOME/bin/java
JAVA_HEAP_MAX=-Xmx1000m

# CLASSPATH initially contains $STRUC_CONF_DIR
CLASSPATH="${CLASSPATH}:${STRUC_CONF_DIR}"
CLASSPATH=${CLASSPATH}:$JAVA_HOME/lib/tools.jar

for jarfile in `ls $STRUC_HOME/lib/.`
do
CLASSPATH="${CLASSPATH}:$STRUC_HOME/lib/$jarfile"
done

if [ "$COMMAND" = "zj_atmosphere" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjAtmosphereCrawler'
  elif [ "$COMMAND" = "qg_atmosphere" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.QgAtmosphereCrawler'
  elif [ "$COMMAND" = "qg_atmosphere_bp" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.QgAtmosphereBpCrawler'
  elif [ "$COMMAND" = "qg_atmosphere_aqi_d" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.QgAtmosphereAQIDayCrawler'
  elif [ "$COMMAND" = "heb_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HebPollutionDataCrawler'
    elif [ "$COMMAND" = "heb_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HebPollutionEnterpriseCrawler'
    elif [ "$COMMAND" = "anh_data_auto" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionDataAutoCrawler'
  elif [ "$COMMAND" = "anh_data_sd" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionDataSDCrawler'
  elif [ "$COMMAND" = "qg_atmosphere_aqi" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.QgAtmosphereAQICrawler'
elif [ "$COMMAND" = "common_crawler" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CommonCrawler'
elif [ "$COMMAND" = "zj_atmosphere_old" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjAtmosphereOldCrawler'
elif [ "$COMMAND" = "zj_atmosphere_gov" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjGovAtmosphereCrawler'
elif [ "$COMMAND" = "zj_pollution_gas" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionGasCrawler'
elif [ "$COMMAND" = "zj_pollution_water" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionWaterCrawler'
elif [ "$COMMAND" = "weather_day" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherDayCrawler'
elif [ "$COMMAND" = "weather_hour" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherHourCrawler'
  elif [ "$COMMAND" = "weather_hour_bp" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherHourBpCrawler'
elif [ "$COMMAND" = "weather_index" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherIndexCrawler'
elif [ "$COMMAND" = "weather_forecast" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherForecastCrawler'
  elif [ "$COMMAND" = "weather_forecast_bp" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WeatherForecastBpCrawler'
elif [ "$COMMAND" = "disaster_alarm" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.DisasterAlarmCrawler'
elif [ "$COMMAND" = "atmosphere" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AtmosphereCrawler'
elif [ "$COMMAND" = "atmosphere_aqi" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AtmosphereAQICrawler'
elif [ "$COMMAND" = "atmosphere_aqi_d" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AtmosphereAQIDayCrawler'
elif [ "$COMMAND" = "traffic" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TrafficCrawler'
elif [ "$COMMAND" = "traffic_index" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TrafficIndexCrawler'
elif [ "$COMMAND" = "traffic_indicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TrafficIndicatorCrawler'
elif [ "$COMMAND" = "disaster_alarm" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.DisasterAlarmCrawler'
elif [ "$COMMAND" = "drink_water" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.DrinkWaterCrawler'
  elif [ "$COMMAND" = "drink_water_hzgx" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.DrinkwaterHzgxCrawler'
elif [ "$COMMAND" = "surface_water" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SurfaceWaterCrawler'

elif [ "$COMMAND" = "weather_f_lifeindex_process" ] ; then
  CLASS='com.zjhcsoft.struc.util.WeatherFLifeindexProcess'
elif [ "$COMMAND" = "hbase_weather_process" ] ; then
  CLASS='com.zjhcsoft.struc.util.HBaseWeatherProcess'
elif [ "$COMMAND" = "hbase_atmosphere_process" ] ; then
  CLASS='com.zjhcsoft.struc.util.HBaseAtmosphereProcess'
  
elif [ "$COMMAND" = "weather_forecast_monitor" ] ; then
  CLASS='com.zjhcsoft.struc.monitor.WeatherForecastMonitoring'
elif [ "$COMMAND" = "zj_atmosphere_day_monitor" ] ; then
  CLASS='com.zjhcsoft.struc.monitor.ZjAtmosphereDayMonitoring'
elif [ "$COMMAND" = "zj_atmosphere_hour_monitor" ] ; then
  CLASS='com.zjhcsoft.struc.monitor.ZjAtmosphereHourMonitoring'
     elif [ "$COMMAND" = "sd_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SdPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "sd_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SdPollutionMonitorsiteCrawler'
 elif [ "$COMMAND" = "sd_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SdPollutionDataCrawler'
  elif [ "$COMMAND" = "hun_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionEnterpriseCrawler'
  elif [ "$COMMAND" = "hun_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionMonitorsiteCrawler'
  elif [ "$COMMAND" = "hun_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionDataCrawler'
  elif [ "$COMMAND" = "hun_pollution_history" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionHistoryCrawler'
  
   elif [ "$COMMAND" = "js_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JsPollutionDataCrawler'
   elif [ "$COMMAND" = "js_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JsPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "js_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JsPollutionMonitorsiteCrawler'
  
   elif [ "$COMMAND" = "hain_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HainPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "hain_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HainPollutionDataCrawler'
   elif [ "$COMMAND" = "hain_pollution_autodata" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HainPollutionDataAutoCrawler'
   elif [ "$COMMAND" = "hain_jd_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HainJdPollutionDataCrawler'
  
   elif [ "$COMMAND" = "zj_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionEntCrawler'
   elif [ "$COMMAND" = "zj_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionSiteCrawler'
   elif [ "$COMMAND" = "zj_pollution_data_sd" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionDataSDCrawler'
   elif [ "$COMMAND" = "zj_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionDataCrawler'
   elif [ "$COMMAND" = "zj_pollution_moni_plan" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZjPollutionMoniPlanCrawler'
  
  elif [ "$COMMAND" = "guiz_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GuizPollutionEnterpriseCrawler'
  elif [ "$COMMAND" = "guiz_pollution_data_manu" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GuizPollutionDataManuCrawler'
  elif [ "$COMMAND" = "guiz_pollution_data_auto_day" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GuizPollutionDataAutoDayCrawler'
  elif [ "$COMMAND" = "guiz_pollution_data_auto_hour" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GuizPollutionDataAutoHourCrawler'
  
  elif [ "$COMMAND" = "fj_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.FjPollutionDataCrawler'
  
  elif [ "$COMMAND" = "tz_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TzTrafficIndicatorCrawler'
  elif [ "$COMMAND" = "ls_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.LsTrafficIndicatorCrawler'
  elif [ "$COMMAND" = "jx_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JxTrafficIndicatorCrawler'
  elif [ "$COMMAND" = "ls_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.LsAreaTrafficCrawler'
  elif [ "$COMMAND" = "jx_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JxAreaTrafficCrawler'
    elif [ "$COMMAND" = "zs_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TrafficZhoushanCrawler'
    elif [ "$COMMAND" = "zs_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ZsTrafficIndicatorCrawler'
    elif [ "$COMMAND" = "wz_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WzTrafficIndexCrawler'
    elif [ "$COMMAND" = "wz_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.WzTrafficIndicatorCrawler'
  	elif [ "$COMMAND" = "jh_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JhTrafficIndexCrawler'
    elif [ "$COMMAND" = "jh_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JhTrafficIndicatorCrawler'
  	elif [ "$COMMAND" = "jh_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.JhTrafficIndexCrawler'
  	elif [ "$COMMAND" = "nb_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.NbTrafficIndexCrawler'
  	elif [ "$COMMAND" = "sx_trafficindicator" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SxTrafficIndicatorCrawler'
  	elif [ "$COMMAND" = "sx_trafficindex" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.SxTrafficIndexCrawler'
  
   elif [ "$COMMAND" = "tz_weather_forecast" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TzWeatherForecastCrawler'
   elif [ "$COMMAND" = "tz_weather_hour" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TzWeatherHourCrawler'
  
   elif [ "$COMMAND" = "sh_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ShPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "sh_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ShPollutionMonitorsiteCrawler'
   elif [ "$COMMAND" = "sh_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.ShPollutionDataCrawler'
  
   elif [ "$COMMAND" = "gd_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GdPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "gd_pollution_gas_hour_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GdPollutionGasHourMoniDataCrawler'
   elif [ "$COMMAND" = "gd_pollution_water_hour_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GdPollutionWaterHourMoniDataCrawler'
     elif [ "$COMMAND" = "gd_pollution_hand_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.GdPollutionHandDataCrawler'
  
   elif [ "$COMMAND" = "anh_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "anh_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionMonitorsiteCrawler'
   elif [ "$COMMAND" = "anh_pollution_auto_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionDataAutoCrawler'
   elif [ "$COMMAND" = "anh_pollution_hand_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.AnhPollutionDataSDCrawler'
  
   elif [ "$COMMAND" = "cq_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "cq_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionMonitorsiteCrawler'
   elif [ "$COMMAND" = "cq_pollution_gas_hand_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionGasHandMoniDataCrawler'
   elif [ "$COMMAND" = "cq_pollution_water_hand_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionWaterHandMoniDataCrawler'
   elif [ "$COMMAND" = "cq_pollution_gas_hour_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionGasHourMoniDataCrawler'
   elif [ "$COMMAND" = "cq_pollution_water_hour_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.CqPollutionWaterHourMoniDataCrawler'  
  
     elif [ "$COMMAND" = "tj_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TjPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "tj_pollution_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TjPollutionMoniDataCrawler'
   elif [ "$COMMAND" = "tj_pollution_hand_moni_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.TjPollutionHandMoniDataCrawler'
	
	 elif [ "$COMMAND" = "bj_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.BjPollutionDataCrawler'
  
     elif [ "$COMMAND" = "hun_pollution_enterprise" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionEnterpriseCrawler'
   elif [ "$COMMAND" = "hun_pollution_monitorsite" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionMonitorsiteCrawler'
   elif [ "$COMMAND" = "hun_pollution_data" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionDataCrawler'
   elif [ "$COMMAND" = "hun_pollution_history" ] ; then
  CLASS='com.zjhcsoft.struc.crawler.HunPollutionHistoryCrawler'
    elif [ "$COMMAND" = "test_connect" ] ; then
  CLASS='com.zjhcsoft.struc.test.Test'
else  
CLASS=$COMMAND
fi

"$JAVA" -Dproc_$COMMAND -XX:OnOutOfMemoryError="kill -9 %p" $JAVA_HEAP_MAX -classpath "$CLASSPATH" $CLASS "$@"