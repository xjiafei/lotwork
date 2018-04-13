package application;
	
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import amber.queryfundreport.QueryBankCardCore;
import amber.queryfundreport.QueryFundLogCore;
import amber.queryfundreport.QueryStatusListener;
import amber.queryfundreport.enums.FundType;
import amber.queryfundreport.util.LogUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Main extends Application implements Initializable {
	
	LogUtil log = new LogUtil(Main.class);
	
	@FXML
	private TextField txtAccount;
	@FXML
	private TextField txtStartDate;
	@FXML
	private TextField txtEndDate;
	@FXML
	private ProgressBar progressbar;
	@FXML
	private ListView<FundType> listviewType;
	@FXML
	private Button btnExport;
	@FXML
	private Button btnExport2;
	@FXML
	private ProgressBar progressbar2;
	@FXML
	private TextArea txtAreaAccount;
	@FXML
	private TextArea txtAreaLog;
	
	private Set<String> selectTypes = new HashSet<>();
	
	private QueryFundLogCore queryFundCore;
	
	private QueryBankCardCore queryBankCore;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setFullScreen(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnExportOnClick() {
		progressbar.setProgress(0L);
		String account = txtAccount.getText();
		String types = "";
		String startTime = txtStartDate.getText();
		String endTime = txtEndDate.getText();
		for(String type:selectTypes){
			types+=type+",";
		}
		if(account!=null && account.length()==0){
			account = null;
		}
		log.doLog("account:"+account+",startDate:"+startTime+",endDate:"+endTime+",types:"+types);
		if(startTime!=null&&endTime!=null&&selectTypes.size()>0){
			try {
				String[] typeArr = new String[selectTypes.size()];
				queryFundCore.execute(account, startTime, endTime, selectTypes.toArray(typeArr));
			} catch (Exception e) {
				log.doLog(e);
			}
		}
	}
	
	@FXML
	private void btnExport2OnClick() {
		progressbar2.setProgress(0L);
		String accounts = txtAreaAccount.getText();
		if(accounts!=null && accounts.length()>0){
			log.doLog("accounts:"+accounts);
			String[] accountList = accounts.split(";");
			try {
				queryBankCore.execute(accountList);
			} catch (Exception e) {
				log.doLog(e);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFundCore();
		initTypeList();
		initBankCore();
	}
	
	private void initFundCore(){
		queryFundCore = new QueryFundLogCore(new QueryStatusListener() {
			@Override
			public void updateProgress(int total, int done) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						progressbar.setProgress((double)done/(double)total);
					}
				});
			}

			@Override
			public synchronized void doLog(String logStr) {
				log.doLog(logStr);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						txtAreaLog.appendText(logStr+"\n");
					}
				});
			}
		});
	}
	
	private void initBankCore(){
		queryBankCore = new QueryBankCardCore(new QueryStatusListener() {
			@Override
			public void updateProgress(int total, int done) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						progressbar2.setProgress((double)done/(double)total);
					}
				});
			}

			@Override
			public synchronized void doLog(String logStr) {
				log.doLog(logStr);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						txtAreaLog.appendText(logStr+"\n");
					}
				});
			}
		});
	}
	
	private void initTypeList(){
		ObservableList<FundType> items = FXCollections.observableArrayList(FundType.values());
		listviewType.setItems(items);
		listviewType.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listviewType.setCellFactory(new Callback<ListView<FundType>, ListCell<FundType>>() {
			@Override
			public ListCell<FundType> call(ListView<FundType> param) {
				return new FundTypeCell();
			}
			
			class FundTypeCell extends ListCell<FundType>{
				@Override
				protected void updateItem(FundType item, boolean empty) {
					super.updateItem(item, empty);
					if(item!=null){
						setText(item.typeName);
					}
				}
			}
		});
		listviewType.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
            	selectTypes.clear();
                ObservableList<FundType> selectedItems =  listviewType.getSelectionModel().getSelectedItems();
                for(FundType s : selectedItems){
                	selectTypes.add(s.typeName);
                }
            }
        });
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
