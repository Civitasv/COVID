import Vue from 'vue'
import Vuex from "vuex"
import virus from "./modules/virus"
import visual from "./modules/visual"
import user from "./modules/user"

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const store = new Vuex.Store({
    modules: {
        virus, visual, user
    },
    strict: debug
})

export default store