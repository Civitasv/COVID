<template>
  <div class="descrip">
    <div class="box">
      <div class="redbox">
        <p class="text">
          <span>确诊</span>
        </p>
        <p class="confirmed">
          <span>{{ confirmed }}</span>
        </p>
      </div>
      <div class="greenbox">
        <p class="text">
          <span>治愈</span>
        </p>
        <p class="recovered">
          <span>{{ recovered }}</span>
        </p>
      </div>
      <div class="graybox">
        <p class="text">
          <span>死亡</span>
        </p>
        <p class="deaths">
          <span>{{ deaths }}</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    confirmed: Number,
    recovered: Number,
    deaths: Number,
  },
};
</script>

<style scoped>
.box {
  text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.5), -1px -1px 0 rgba(0, 0, 0, 0.5),
    -1px 1px 0 rgba(0, 0, 0, 0.5), 1px -1px 0 rgba(0, 0, 0, 0.5);
}

.redbox .text,
.greenbox .text,
.graybox .text {
  text-align: left;
  color: #fff;
  margin: 10px 10px 0 0;
  position: relative;
  padding: 5px 0 5px 10px;
  font-size: 16px;
  line-height: 16px;
  font-weight: 700;
  text-shadow: 1px 1px 1px #000, 1px 1px 1px #fff;
}
.redbox .text:before,
.greenbox .text:before,
.graybox .text:before {
  position: absolute;
  right: -10px;
  bottom: 0;
  content: "";

  border-style: solid;
  border-width: 5px;
  width: 0;
  height: 0;
  pointer-events: none;
}
.redbox .text:before {
  border-bottom: 5px solid rgba(251, 103, 103, 0.5);
  border-right: 5px solid rgba(251, 103, 103, 0.5);
  border-color: rgba(251, 103, 103, 0.5) transparent transparent
    rgba(251, 103, 103, 0.5);
}
.greenbox .text:before {
  border-bottom: 5px solid rgba(40, 218, 111, 0.5);
  border-right: 5px solid rgba(40, 218, 111, 0.5);
  border-color: rgba(40, 218, 111, 0.5) transparent transparent
    rgba(40, 218, 111, 0.5);
}

.graybox .text:before {
  border-bottom: 5px solid rgba(148, 159, 165, 0.5);
  border-right: 5px solid rgba(148, 159, 165, 0.5);
  border-color: rgba(148, 159, 165, 0.5) transparent transparent
    rgba(148, 159, 165, 0.5);
}
.redbox .text:after,
.greenbox .text:after,
.graybox .text:after {
  position: absolute;
  right: -10px;
  bottom: 10px;
  top: 0;
  content: "";
  width: 10px;
  pointer-events: none;
}
.redbox .text:after {
  background: rgba(251, 103, 103, 0.5);
}
.greenbox .text:after {
  background: rgba(40, 218, 111, 0.5);
}
.graybox .text:after {
  background: rgba(148, 159, 165, 0.5);
}
.redbox .text {
  background: rgba(251, 103, 103, 0.5);
  border-left: 5px solid #fb6767;
}
.greenbox .text {
  background: rgba(40, 218, 111, 0.5);
  border-left: 5px solid #28da6f;
}
.graybox .text {
  background: rgba(148, 159, 165, 0.5);
  border-left: 5px solid #949fa5;
}
.redbox .confirmed,
.greenbox .recovered,
.graybox .deaths {
  font-size: 30px;
  margin: 5px 0;
}
.redbox .confirmed {
  color: #f99;
}
.greenbox .recovered {
  color: #28ca68;
}
.graybox .deaths {
  color: #dadada;
}
</style>