package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NavigationState {
    private final Stack<String> navigationStack;
    private final Stack<String> companySymbolStack; // Track company symbols for company details navigation

    public NavigationState() {
        this.navigationStack = new Stack<>();
        this.companySymbolStack = new Stack<>();
    }

    public void pushView(String viewName) {
        navigationStack.push(viewName);
    }

    public void pushCompanyView(String viewName, String companySymbol) {
        navigationStack.push(viewName);
        companySymbolStack.push(companySymbol);
    }

    public String popView() {
        return navigationStack.isEmpty() ? null : navigationStack.pop();
    }

    public String popCompanySymbol() {
        return companySymbolStack.isEmpty() ? null : companySymbolStack.pop();
    }

    public boolean canGoBack() {
        return !navigationStack.isEmpty();
    }

    public List<String> getCompanyBreadcrumbs() {
        return new ArrayList<>(companySymbolStack);
    }

    public void clear() {
        navigationStack.clear();
        companySymbolStack.clear();
    }
}
