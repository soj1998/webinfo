package wl.test.craw;

/**
 * ����״̬ʵ���� ͳ��������Ϣ
 * @author
 *
 */
public class CrawlStat {
  private int totalProcessedPages; //�����ҳ������
  private long totalLinks; // ��������
  private long totalTextSize; // ���ı�����
 
  public int getTotalProcessedPages() {
    return totalProcessedPages;
  }
 
  public void setTotalProcessedPages(int totalProcessedPages) {
    this.totalProcessedPages = totalProcessedPages;
  }
 
  /**
   * �ܴ���ҳ������1
   */
  public void incProcessedPages() {
    this.totalProcessedPages++;
  }
 
  public long getTotalLinks() {
    return totalLinks;
  }
 
  public void setTotalLinks(long totalLinks) {
    this.totalLinks = totalLinks;
  }
 
  public long getTotalTextSize() {
    return totalTextSize;
  }
 
  public void setTotalTextSize(long totalTextSize) {
    this.totalTextSize = totalTextSize;
  }
 
  /**
   * ����������count��
   * @param count
   */
  public void incTotalLinks(int count) {
    this.totalLinks += count;
  }
 
  /**
   * ���ı����ȼ�total��
   * @param count
   */
  public void incTotalTextSize(int count) {
    this.totalTextSize += count;
  }
}