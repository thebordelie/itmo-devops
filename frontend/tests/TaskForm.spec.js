import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import { flushPromises } from "./utils/flushPromises";
import TaskForm from "../src/components/TaskForm.vue";

vi.mock("../src/api/tasks", () => ({
  default: {
    create: vi.fn()
  }
}));

import taskApi from "../src/api/tasks";

describe("TaskForm", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test("submits form, calls API with userId and emits created", async () => {
    taskApi.create.mockResolvedValueOnce({ data: { id: 123 } });

    const wrapper = mount(TaskForm, {
      global: {
        plugins: [
          createTestingPinia({
            initialState: {
              user: { id: 7, name: "Temp User" }
            }
          })
        ]
      }
    });

    const titleInput = wrapper.find('input[placeholder="Название"]');
    await titleInput.setValue("New Task");

    await wrapper.find("form").trigger("submit.prevent");
    await flushPromises();

    expect(taskApi.create).toHaveBeenCalledTimes(1);

    const payload = taskApi.create.mock.calls[0][0];
    expect(payload.title).toBe("New Task");
    expect(payload.userId).toBe(7);

    expect(wrapper.emitted()).toHaveProperty("created");
  });
});