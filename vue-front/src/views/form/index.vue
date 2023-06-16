<template>
  <div>
    <p>你可以在这里上传你想要分享的图片</p>
    <el-upload
      class="upload-demo"
      action="http://localhost:9999/userFile/upload"
      :data="uploadData"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :file-list="fileList"
      list-type="picture">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>

<script>
import userAPI from '@/api/userManage'
import JSZip from "jszip";
export default {
  data() {
    return {
      fileList: [
        {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'},
        {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      uploadData: {
        uploader_id: this.$store.state.user.name
      }
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    fetchFileList() {
      userAPI.getFileList()
        .then(res => {
          // 获取文件列表数据
          const fileData = res.data;

          // 处理每个文件的数据
          fileData.forEach(file => {
            const fileName = file.name;

            // 将字节数组转换为字符串
            const content = new TextDecoder('utf-8').decode(file.content);

            // 创建一个 Blob 对象
            const blob = new Blob([content]);

            // 创建一个 URL 对象或将文件内容转为 Base64 字符串
            // const url = URL.createObjectURL(blob);
            const dataURL = 'data:image/jpeg;base64,' + window.btoa(content);

            // 将文件信息保存到 fileList 中
            this.fileList.push({
              name: fileName,
              url: dataURL
            });
            console.log(this.fileList)
            // 释放 URL 对象（如果使用了 URL.createObjectURL）
            // URL.revokeObjectURL(url);
          });
        })
        .catch(err => {
          console.log(err);
        });
    }



  },
  mounted() {
    // this.fetchFileList()
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

