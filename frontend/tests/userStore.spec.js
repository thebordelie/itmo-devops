import { createPinia, setActivePinia } from "pinia";
import { useUserStore } from "../src/stores/userStore";

describe("userStore", () => {
  beforeEach(() => {
    setActivePinia(createPinia());
    localStorage.clear();
  });

  test("setUser sets id and name", () => {
    const store = useUserStore();
    store.setUser({ id: 5, name: "Test" });

    expect(store.id).toBe(5);
    expect(store.name).toBe("Test");
  });

  test("logout clears user", () => {
    const store = useUserStore();
    store.setUser({ id: 5, name: "Test" });

    store.logout();

    expect(store.id).toBe(null);
    expect(store.name).toBe(null);
  });
});