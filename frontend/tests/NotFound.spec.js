import { mount } from "@vue/test-utils"
import NotFoundView from "../src/views/NotFoundView.vue"

test("renders 404 message", () => {
  const wrapper = mount(NotFoundView)

  expect(wrapper.text()).toContain("404")
})