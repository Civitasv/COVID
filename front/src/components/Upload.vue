<template>
  <div class="upload">
    <h1>数据录入</h1>
    <el-upload
      class="upload-demo"
      drag
      action="http://localhost/virus/"
      :multiple="false"
      :show-file-list="true"
      :on-success="handleSuccess"
      accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  data() {
    return {
      file: ""
    };
  },
  methods: {
    handleSuccess(res) {
      alert(res);
    }
  }
};
</script>

<style scoped>
.upload {
  text-align: center;
  padding-top: 25vh;
}
.upload h1 {
  padding-bottom: 5vh;
}
</style>