# Contributing Guidelines

## Issue Tracking

We use issues to track progress in the project.

### How to create an Issue:
1. Go to `Issues` in the sidebar and create a new issue.
2. Add a title for the issue
   - Start with one of the following tags:
     - `[FEATURE]` 
     - `[BUG]`
     - `[DOCUMENTATION]`
     - `[TESTING]`
   - Then put a fitting title.
   - *Example.* `[FEATURE] Implement CLERIC logic`
3. Add a short description for the issue, depicting what will be added/changed. Add screenshots if necessary.
4. If necessary, assign a team member.
5. `Epic` and `Milestone` are not important and may be skipped.
6. Select `labels` relevant to the issue.
7. Select a `weight` for the issue based on the estimated time required:

    | Weight | Title      | Estimated time  |
    |--------|------------|-----------------|
    | 1      | Small      | (*few minutes*) |
    | 2      | Normal     | (*15m - 1h*)    |
    | 3      | Large      | (*1h - 3h*)     |
    | 4      | Very Large | (*3h+)          |

8. As `Due Date` put the deadline of the lab.
9. `Iteration` may be skipped.

## Branching strategy

### Creating a Branch:

1. On the left sidebar, select **Code > Branches**.
2. In the upper-right corner, select **New branch**.
3. Enter a **Branch name**
   - based of the tag and title of the corresponding issue: `tag/title`.
   - All lower-case.
   - *Example.* `feature/cleric-logic`
4. In Create from, select the **base of your branch**: normally the `main` branch.
5. Select **Create branch**.

### Switch to the branch locally
To switch to the new branch, you can use the following command in your CLI.
```shell
git checkout <branch-name>
```

### Committing to the branch
To commit, you can use the following commands in your CLI.
```shell
git add .
git push -m "commit message"
git push
```

### Commit message convention

Commit messages should use the following convention: `[<issue-number>] <prefix>: <short description>`
- You can skip the `[<issue-number>]` if the commit is not related to any issue.
- The `prefix` should be picked out of the following table:

    | Prefix   | Full name                | Description                                                                                            |
    |----------|--------------------------|--------------------------------------------------------------------------------------------------------|
    | feat     | Features                 | A new feature                                                                                          |
    | fix      | Bug Fixes                | A bug fix                                                                                              |
    | docs     | Documentation            | Documentation only changes                                                                             |
    | style    | Styles                   | Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc) |
    | refactor | Code Refactoring         | A code change that neither fixes a bug nor adds a feature                                              |
    | perf     | Performance Improvements | A code change that improves performance                                                                |
    | test     | Tests                    | Adding missing tests or correcting existing tests                                                      |
    | build    | Builds                   | Changes that affect the build system or external dependencies (maven)                                  |
    | ci       | Continuous Integrations  | Changes to our CI configuration files and scripts (GitLab CI file)                                     |
    | chore    | Chores                   | Other changes that don't modify src or test files                                                      |
    | revert   | Reverts                  | Reverts a previous commit                                                                              |