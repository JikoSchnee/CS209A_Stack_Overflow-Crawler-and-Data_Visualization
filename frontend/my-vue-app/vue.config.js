const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      '/apii':{
        target:'http://localhost:8080',
        changeOrigin: true,
        ws: true,
        // onProxyReq: function (proxyReq) {
        //   proxyReq.removeHeader('origin')
        // },
        pathRewrite:{
          '^/apii':''
        }
      }
    }

  }
})
