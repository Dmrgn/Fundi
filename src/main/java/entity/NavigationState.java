package entity;

import java.util.Stack;

public class NavigationState {
    private final Stack<String> navigationStack;

    public NavigationState() {
        this.navigationStack = new Stack<>();
    }

    public void pushView(String viewName) {
        navigationStack.push(viewName);
    }

    public String popView() {
        return navigationStack.isEmpty() ? null : navigationStack.pop();
    }

    public boolean canGoBack() {
        return !navigationStack.isEmpty();
    }

    public void clear() {
        navigationStack.clear();
    }
}
