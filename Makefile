run: all
	java -classpath bin/:. imgVwr
all: BaseView.java ExplorerView.java Image.java imgVwr.java KeywordsView.java Language.java LangView.java MenuView.java ViewerView.java TreeView.java
	javac $^ -d bin/
