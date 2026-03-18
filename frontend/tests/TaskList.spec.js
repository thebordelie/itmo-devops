import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import { flushPromises } from "./utils/flushPromises";
import TaskList from "../src/components/TaskList.vue";

vi.mock("../src/api/tasks", () => ({
  default: {
    getAll: vi.fn(),
    delete: vi.fn(),
    update: vi.fn(),
    create: vi.fn()
  }
}));

import taskApi from "../src/api/tasks";

describe("TaskList", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test("renders tasks from API", async () => {
    taskApi.getAll.mockResolvedValueOnce({
      data: [
        { id: 1, title: "Task 1", description: "desc", status: "NEW", priority: "LOW", dueDate: "2026-03-10" },
        { id: 2, title: "Task 2", description: "desc", status: "DONE", priority: "HIGH", dueDate: "2026-03-11" }
      ]
    });

    const wrapper = mount(TaskList, {
      global: {
        plugins: [
          createTestingPinia({
            initialState: {
              user: { id: 1, name: "Test User" }
            }
          })
        ]
      }
    });

    await flushPromises();

    expect(wrapper.text()).toContain("Task 1");
    expect(wrapper.text()).toContain("Task 2");
    expect(taskApi.getAll).toHaveBeenCalled();
  });

  test("shows empty state when no tasks", async () => {
    taskApi.getAll.mockResolvedValueOnce({ data: [] });

    const wrapper = mount(TaskList, {
      global: {
        plugins: [createTestingPinia({ initialState: { user: { id: 1, name: "Test User" } } })]
      }
    });

    await flushPromises();

    expect(wrapper.text()).toContain("Нет задач");
  });
});