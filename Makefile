VIEWS=BaseView.java ExplorerView.java KeywordsView.java LangView.java MenuView.java ViewerView.java TreeView.java
VIEW=$(addprefix view/, $(VIEWS))
MODELS=Image.java Language.java
MODEL=$(addprefix model/, $(MODELS))

run: all
	java -classpath bin/:. imgVwr
all: imgVwr.java view model
	javac $< -d bin/
view: $(VIEW)
	javac $^ -d bin/
model: $(MODEL)
	javac $^ -d bin/
