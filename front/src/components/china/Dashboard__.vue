<template>
  <div id="china">
    <div class="header">
      <h1 class="header-title">中国疫情数据可视化</h1>
    </div>
    <div class="wrapper">
      <div class="content">
        <div class="col col-l">
          <div class="xpanel-wrapper xpanel-wrapper-40">
            <div class="xpanel xpanel-l-t">
              <div class="title">日新增量</div>
              <Calendar></Calendar>
            </div>
          </div>
          <div class="xpanel-wrapper xpanel-wrapper-60">
            <div class="xpanel xpanel-l-b">
              <div class="title">死亡率</div>
              <Mortality></Mortality>
            </div>
          </div>
        </div>
        <div class="col col-c">
          <div class="xpanel-wrapper xpanel-wrapper-75">
            <div class="xpanel no-bg">
              <GeoServer></GeoServer>
            </div>
          </div>
          <div class="xpanel-wrapper xpanel-wrapper-25">
            <div class="xpanel xpanel-c-b">
              <div class="title title-long">ssssssss</div>
            </div>
          </div>
        </div>
        <div class="col col-r">
          <div class="xpanel-wrapper xpanel-wrapper-25">
            <div class="xpanel xpanel-r-t">
              <div class="title">日历图</div>
            </div>
          </div>
          <div class="xpanel-wrapper xpanel-wrapper-30">
            <div class="xpanel xpanel-r-m">
              <div class="title">日历图</div>
            </div>
          </div>
          <div class="xpanel-wrapper xpanel-wrapper-45">
            <div class="xpanel xpanel-r-b">
              <div class="title">日治愈量</div>
              <Calendar2></Calendar2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("../../assets/china/css/app.css");
</style>

<script>
const Calendar = () => import("../calendar/Calendar");
const Calendar2 = () => import("../calendar/Calendar2");
const Mortality = () => import("../time/ChinaMortality");
const GeoServer = () => import("../geoserver/GeoServer");

export default {
  components: {
    Calendar,
    Calendar2,
    GeoServer,
    Mortality
  }
};
</script>