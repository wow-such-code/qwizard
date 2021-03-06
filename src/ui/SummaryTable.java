package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AOpenbisSample;

import com.google.gwt.user.client.AsyncProxy.DefaultValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Table to summarize prepared samples, remove them or adapt their secondary names
 * @author Andreas Friedrich
 *
 */
public class SummaryTable extends VerticalLayout {

  private Table table;
  private Map<String, AOpenbisSample> map;

  /**
   * Create a new summary table
   * @param name Title of the table
   * @param samples List of prepared AOpenbisSamples
   */
  public SummaryTable(String name, List<AOpenbisSample> samples) {
    map = new HashMap<String, AOpenbisSample>();
    table = new Table(name);
    table.addContainerProperty("ID", String.class, null);
    table.addContainerProperty("Secondary Name", String.class, null);
    table.addContainerProperty("Remove", Button.class, null);
    for (int i = 0; i < samples.size(); i++) {
      AOpenbisSample s = samples.get(i);
      String id = Integer.toString(i + 1);
      map.put(id, s);

      // The Table item identifier for the row.
      Integer itemId = new Integer(i);

      // Create a button and handle its click.
      Button delete = new Button("X");
      delete.setWidth("26px");
      delete.setHeight("26px");
      delete.setData(itemId);
      delete.addClickListener(new Button.ClickListener() {
        @Override
        public void buttonClick(ClickEvent event) {
          // Get the item identifier from the user-defined data.
          Integer iid = (Integer) event.getButton().getData();
          table.removeItem(iid);
        }
      });

      // Create the table row.
      table.addItem(new Object[] {id, s.getQ_SECONDARY_NAME(), delete}, itemId);
      table.getItem(itemId).getItemProperty("ID").setReadOnly(true);
    }
    addComponent(table);
    table.setEditable(true);
    table.setImmediate(true);
  }

  public List<AOpenbisSample> getSamples() {
    List<AOpenbisSample> res = new ArrayList<AOpenbisSample>();
    for (Object id : table.getItemIds()) {
      String key = (String) table.getItem(id).getItemProperty("ID").getValue();
      AOpenbisSample s = map.get(key);
      s.setQ_SECONDARY_NAME((String) table.getItem(id).getItemProperty("Secondary Name").getValue());
      res.add(s);
    }
    return res;
  }

  public void setPageLength(int size) {
    table.setPageLength(size);
  }

}
