package codingblackfemales.ui.module;

import org.finos.vuu.api.TableDef;
import org.finos.vuu.core.module.DefaultModule;
import org.finos.vuu.core.module.ModuleFactory;
import org.finos.vuu.core.module.TableDefContainer;
import org.finos.vuu.core.module.ViewServerModule;
import org.finos.vuu.core.table.Columns;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static scala.jdk.javaapi.CollectionConverters.asScala;

public class MyModule extends DefaultModule {

    public static final String NAME = "MY_MOD";

    public ViewServerModule create(final TableDefContainer tableDefContainer){
        return ModuleFactory.withNamespace(NAME, tableDefContainer)
                .addTable(TableDef.apply(
                                "myTable",
                                "id",
                                Columns.fromNames(asScala(asList("id:String", "foo:String", "myInt:Int")).toSeq()),
                                asScala(new ArrayList<String>()).toSeq()
                        ),
                        (table, vs) -> new MyProvider(table)
                ).asModule();
    }
}
