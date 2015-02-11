run: all
	java -classpath bin/ imgVwr
all: BaseView.java ExplorerView.java Image.java imgVwr.java KeywordsView.java LangView.java ViewerView.java TreeView.java
	javac $^ -d bin/
