import Vue from 'vue'
import Vuex from "vuex"
import virus from "./modules/virus"

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const store = new Vuex.Store({
    modules: {
        virus
    },
    strict: debug
})

export default store