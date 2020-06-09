module.exports = {
  devServer: {
    // 运行完即打开浏览器
    open: true,
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://47.100.65.60/', // 不要加http:
        ws: true,
        changeOrigin: true,
        // rewrite path
        pathRewrite: { '^/api': '' }
      }
    }
  },
  pages: {
    index: {
      // page 的入口
      entry: 'src/main.js',
      // 模板来源
      template: 'public/index.html',
      // 在 dist/index.html 的输出
      filename: 'index.html',
      // 当使用 title 选项时，
      // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
      title: 'COVID',
      // 在这个页面中包含的块，默认情况下会包含
      // 提取出来的通用 chunk 和 vendor chunk。
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  },

  publicPath: "",
  css: {
    loaderOptions: {
      css: {},
      less: {},
      sass: {},
      postcss: {
      }
    }
  }
}
