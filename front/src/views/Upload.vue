<template>
  <div class="upload">
    <h1>数据录入</h1>
    <Upload
      multiple
      type="drag"
      :format="['xls','xlsx']"
      :on-success="handleSuccess"
      :on-format-error="handleFormatError"
      action="http://localhost:8080/upload"
    >
      <div style="padding: 20px 0">
        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
        <p>Click or drag files here to upload</p>
      </div>
    </Upload>
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
    handleFormatError() {
      alert("文件格式不合法!");
    },
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