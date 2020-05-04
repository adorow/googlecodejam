Running interactive problems
----------------------------

Google provided a local testing tool.

To run solutions against the local testing tool a command like this is required (provided the right names):

```jshelllanguage
javac -d . src/main/java/Solution.java && python interactive_runner.py python testing_tool.py 0 -- java Solution
```
