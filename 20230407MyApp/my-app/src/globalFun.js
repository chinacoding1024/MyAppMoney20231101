import Vue from "vue"

Vue.mixin({
	methods: {
		hasAuth(perm) {
			var authority = this.$store.state.user.buttons
			return authority.indexOf(perm) > -1
		}
	}
})