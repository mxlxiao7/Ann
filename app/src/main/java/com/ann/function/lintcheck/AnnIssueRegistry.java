//package com.ann.function.android.lintcheck;
//
//import com.android.tools.lint.client.api.IssueRegistry;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class AnnIssueRegistry extends IssueRegistry {
//
//    @Override
//    public synchronized List getIssues() {
//        System.out.println("==== Ann lint start ====");
//        return Arrays.asList(LogDetector.ISSUE, NewThreadDetector.ISSUE);
//    }
//}