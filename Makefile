run: all
	java -classpath bin/ imgVwr
all: BasePanel.java ExplorerView.java Image.java imgVwr.java KeywordsView.java LangView.java ViewerView.java
	javac $^ -d bin/
