package design;

import java.awt.*;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import model.ThongKeLoaiSPNgay;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;


public class Chart extends JPanel {

    private static final Color[] PALETTE = new Color[]{
        new Color(0, 168, 168),  // Teal
        new Color(230, 126, 34), // Cam
        new Color(41, 128, 185), // Xanh dương
        new Color(142, 68, 173), // Tím
        new Color(39, 174, 96),  // Xanh lá
        new Color(231, 76, 60),  // Đỏ
        new Color(52, 73, 94)    // Xám than
    };

    private static final String[] DAY = new String[]{
        "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"
    };

    private final DateTimeFormatter dtfNgay = DateTimeFormatter.ofPattern("dd/MM");

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private CategoryPlot plot;
    private BarRenderer renderer;

    public Chart() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initChart();
    }

    
   private void initChart() {
    DefaultCategoryDataset emptyDataset = new DefaultCategoryDataset();

    chart = ChartFactory.createBarChart(
            null,                           // Chart title
            null,                           // Domain axis label (X)
            "Số lượng",                     // Range axis label (Y)
            emptyDataset,                   // Dataset
            PlotOrientation.VERTICAL,       // Orientation
            true,                           // Include legend
            true,                           // Tooltips
            false                           // URLs
    );

    chart.setBackgroundPaint(Color.WHITE);
    chart.setPadding(new RectangleInsets(2, 2, 2, 2));

    // Tinh chỉnh Loại sản phẩm
    LegendTitle legend = chart.getLegend();
    if (legend != null) {
        legend.setPosition(RectangleEdge.TOP);
        legend.setItemFont(new Font("Segoe UI", Font.PLAIN, 11));
        legend.setBackgroundPaint(Color.WHITE);
        legend.setPadding(new RectangleInsets(10,10,10,10));
    }

    plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlineVisible(false);

    // Lưới ngang trục Y
    plot.setRangeGridlinePaint(new Color(238, 240, 242));
    plot.setRangeGridlineStroke(new BasicStroke(1.0f));
    plot.setDomainGridlinesVisible(false);

    // Thông báo khi không có dữ liệu
    plot.setNoDataMessage("Tuần này chưa phát sinh dữ liệu bán hàng");
    plot.setNoDataMessageFont(new Font("Segoe UI", Font.BOLD, 14));
    plot.setNoDataMessagePaint(new Color(150, 160, 170));

    // Trục hoành X (Thứ & Ngày)
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new Font("Segoe UI", Font.BOLD, 10));
    domainAxis.setTickLabelPaint(new Color(50, 60, 70));
    domainAxis.setCategoryMargin(0.12);
    domainAxis.setLowerMargin(0.01);
    domainAxis.setUpperMargin(0.01);
    
    // Trục tung Y (Số lượng)
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 11));
    rangeAxis.setLabelPaint(new Color(110, 120, 130));
    rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
    rangeAxis.setTickLabelPaint(new Color(110, 120, 130));
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setAxisLinePaint(new Color(190, 200, 210));
    rangeAxis.setAxisLineStroke(new BasicStroke(1.5f));

    // Renderer cho các cột
    renderer = new BarRenderer();
    renderer.setBarPainter(new StandardBarPainter());
    renderer.setShadowVisible(false);
    renderer.setDrawBarOutline(true);
    renderer.setItemMargin(0.1);
   renderer.setDefaultItemLabelsVisible(false);
 // renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());

    renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator(
            "{1} - {0}: {2} sản phẩm",
            NumberFormat.getIntegerInstance()
    ));

    plot.setRenderer(renderer);

    chartPanel = new ChartPanel(chart);
    chartPanel.setBackground(Color.WHITE);
    chartPanel.setOpaque(true);
    chartPanel.setDisplayToolTips(true);
    chartPanel.setDomainZoomable(false);
    chartPanel.setRangeZoomable(false);
    chartPanel.setPopupMenu(JPopupMenu.class.cast(chartPanel.getPopupMenu()));

    chartPanel.setMinimumDrawWidth(0);
    chartPanel.setMinimumDrawHeight(0);
    chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
    chartPanel.setMaximumDrawHeight(Integer.MAX_VALUE);

    chartPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            chartPanel.revalidate();
            chartPanel.repaint();
        }
    });

    add(chartPanel, BorderLayout.CENTER);
}

    
    public void setData(ArrayList<LocalDate> days, Map<String, String> allCategories, ArrayList<ThongKeLoaiSPNgay> rawData) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    if (days == null || days.isEmpty() || allCategories == null || allCategories.isEmpty()) {
        plot.setDataset(dataset);
        return;
    }

    // Lấy danh sách mã loại và sắp xếp thứ tự
    ArrayList<String> categoryOrder = new ArrayList<>(allCategories.keySet());
    categoryOrder.sort(String::compareTo);

    Map<String, Integer> salesData = new HashMap<>();

    if (rawData != null) {
        for (ThongKeLoaiSPNgay item : rawData) {
            if (item.getNgayBan() != null && item.getMaLoaiSP() != null) {
                String key = item.getNgayBan().toString() + "_" + item.getMaLoaiSP();
                salesData.put(key, item.getTongSoLuongBan());
            }
        }
    }

    // Thiết lập màu sắc cho cột
    for (int i = 0; i < categoryOrder.size(); i++) {
        Color color = PALETTE[i % PALETTE.length];
        renderer.setSeriesPaint(i, color);
        renderer.setSeriesOutlinePaint(i, color.darker());
    }

    // Đổ dữ liệu vào dataset 
    for (int i = 0; i < days.size(); i++) {
        LocalDate date = days.get(i);
        String thuName = (i < DAY.length) ? DAY[i] : ("Ngày " + (i + 1));
        String dayLabel = thuName + " (" + date.format(dtfNgay) + ")";

        for (String maLoai : categoryOrder) {
            String tenLoai = allCategories.getOrDefault(maLoai, maLoai);
            String key = date.toString() + "_" + maLoai;
            int qty = salesData.getOrDefault(key, 0);

            dataset.addValue(qty, tenLoai, dayLabel);
        }
    }

    plot.setDataset(dataset);

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setAutoRangeIncludesZero(true); // Luôn hiện gốc 0 ở chân biểu đồ
    rangeAxis.setAutoRange(true);             // Tự tính khoảng Max Y theo kích thước JPanel
    rangeAxis.setUpperMargin(0.12);           // Chừa 12% khoảng trống phía trên đỉnh cột
}

    public JFreeChart getChart() {
        return chart;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public Color getCategoryColor(int index) {
        return PALETTE[index % PALETTE.length];
    }
}
