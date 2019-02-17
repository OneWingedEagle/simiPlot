package graphic;

import java.io.PrintStream;
import java.util.Arrays;




public class Vect
{
  public  double[] el;
  public int length;


  public Vect() {}
  

  public Vect(int I)
  {
    length = I;
    el = new double[I];
  }
  
  public Vect(double[] array) {
    length = array.length;
    el = Arrays.copyOf(array, length);
  }
  
  public Vect(double x, double y, double z) {
    length = 3;
    el = new double[length];
    el[0] = x;el[1] = y;el[2] = z;
  }
  
  public Vect(double x, double y) {
    length = 2;
    el = new double[length];
    el[0] = x;el[1] = y;
  }
  
  public void set(double[] u)
  {
    length = u.length;
    el = Arrays.copyOf(u, length);
  }
  

  public Vect deepCopy()
  {
    Vect w = new Vect(length);
    el = Arrays.copyOf(el, length);
    return w;
  }
  
  public void set(int[] u) {
    for (int i = 0; i < u.length; i++)
      el[i] = u[i];
    length = u.length;
  }
  
  public Vect add(Vect v)
  {
    if (length != length) throw new NullPointerException("vectrs have different lengths");
    Vect w = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] += el[i];
    return w;
  }
  
  public Vect sub(Vect v)
  {
    if (length != length) throw new NullPointerException("vectrs have different lengths");
    Vect w = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] -= el[i];
    return w;
  }
  
  public void rand() {
    for (int i = 0; i < length; i++) {
      el[i] = Math.random();
    }
  }
  
  public Vect ones(int I)
  {
    Vect v = new Vect(I);
    for (int i = 0; i < I; i++)
      el[i] = 1.0D;
    return v;
  }
  
  public Vect flr() {
    int I = length;
    Vect v = new Vect(I);
    for (int i = 0; i < I; i++)
      el[i] = Math.floor(el[i]);
    return v;
  }
  
  public Vect rand(int I, double a, double b)
  {
    Vect v = new Vect(I);
    for (int i = 0; i < I; i++)
      el[i] = (a + (b - a) * Math.random());
    return v;
  }
  
  public Vect mul(Vect u) {
    if (length != length) throw new IllegalArgumentException("vectrs have different lengths");
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] *= el[i];
    return v;
  }
  


  public void rand(double a, double b)
  {
    for (int i = 0; i < length; i++) {
      el[i] = (a + (b - a) * Math.random());
    }
  }
  
  public Vect rand(int I, int a, int b) {
    Vect v = new Vect(I);
    for (int i = 0; i < I; i++)
      el[i] = (a + (b - a) * Math.random());
    return v;
  }
  

  public Vect linspace(double a, double b, int I)
  {
    Vect v = new Vect(I + 1);
    double d = (b - a) / I;
    el[0] = a;
    for (int i = 1; i < I; i++)
      el[i] = (a + i * d);
    el[I] = b;
    return v;
  }
  
  public Vect linspace(double a, double b, double d) {
    double r = (b - a) / d;
    int N = (int)r;
    return linspace(a, b, N);
  }
  
  public Vect sqspace(double a, double b, int I)
  {
    Vect v = new Vect(I);
    double d = (b - a) / (I - 1);
    el[0] = 0.0D;
    for (int i = 1; i < I; i++)
      el[i] = (el[(i - 1)] + 1.0D + 0.2D * Math.abs(i - I / 2.0D));
    v = v.times((b - a) / el[(I - 1)]);
    for (int i = 0; i < I; i++)
      el[i] += a;
    return v;
  }
  
  public Vect randspace(double a, double b, int I, double r)
  {
    Vect v = new Vect();
    double d = (b - a) / (I - 1);
    v = v.rand(I, -r * d, r * d);
    v = v.add(v.linspace(a, b, I));
    el[0] = a;
    el[(I - 1)] = b;
    
    return v;
  }
  
  public Vect times(double a)
  {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] = (a * el[i]);
    return v;
  }
  
  public void timesVoid(double a)
  {
    for (int i = 0; i < length; i++) {
      el[i] = (a * el[i]);
    }
  }
  
  public Vect add(double a) {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] = (a + el[i]);
    return v;
  }
  
  public Vect times(int a)
  {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] = (a * el[i]);
    return v;
  }
  
  public Vect abs()
  {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++)
      el[i] = Math.abs(el[i]);
    return v;
  }
  
  public Vect sqrt()
  {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++) {
      if (el[i] < 0.0D) throw new IllegalArgumentException("Square root error: Entry" + Integer.toString(i) + " is less than zero.");
      el[i] = Math.sqrt(el[i]);
    }
    return v;
  }
  
  public double sum() {
    double sum = 0.0D;
    for (int i = 0; i < length; i++) {
      sum += el[i];
    }
    return sum;
  }
  
  public double max() {
    double max = el[0];
    for (int i = 1; i < length; i++) {
      if (el[i] > max)
        max = el[i];
    }
    return max;
  }
  

  public double min()
  {
    double min = el[0];
    for (int i = 1; i < length; i++) {
      if (el[i] < min)
        min = el[i];
    }
    return min;
  }
  
  public double dot(Vect u)
  {
    if (length != length) throw new IllegalArgumentException("vectrs have different lengths");
    double s = 0.0D;
    for (int i = 0; i < length; i++)
      s += el[i] * el[i];
    return s;
  }
  
  public double norm2()
  {
    double s = 0.0D;
    for (int i = 0; i < length; i++)
      s += el[i] * el[i];
    return s;
  }
  
  public Vect inv() {
    Vect v = new Vect(length);
    for (int i = 0; i < length; i++) {
      if (el[i] == 0.0D) throw new IllegalArgumentException("Divided by zerp error: Entry " + Integer.toString(i) + " is zero.");
      el[i] = (1.0D / el[i]);
    }
    return v;
  }
  
  public void timesVoid(Vect D) { if (length != length) { throw new IllegalArgumentException("vectrs have different lengths");
    }
    for (int i = 0; i < length; i++)
      el[i] *= el[i];
  }
  
  public Vect times(Vect D) {
    Vect v = new Vect(length);
    if (length != length) { throw new IllegalArgumentException("vectrs have different lengths");
    }
    for (int i = 0; i < length; i++) {
      el[i] *= el[i];
    }
    return v;
  }
  
  public Vect v3() {
    int L = length;
    if (L > 2) return this;
    Vect v = new Vect(3);
    for (int i = 0; i < length; i++) {
      el[i] = el[i];
    }
    return v;
  }
  
  public Vect v2() {
    return new Vect(el[0], el[1]);
  }
  
  public Vect div(Vect D) {
    Vect v = new Vect(length);
    if (length != length) { throw new IllegalArgumentException("vectrs have different lengths");
    }
    for (int i = 0; i < length; i++) {
      if (el[i] == 0.0D) throw new IllegalArgumentException("Divided by zerp error: Entry " + Integer.toString(i) + " is zero.");
      el[i] /= el[i];
    }
    return v;
  }
  
  public Vect div0(Vect D) {
    Vect v = new Vect(length);
    if (length != length) { throw new IllegalArgumentException("vectrs have different lengths");
    }
    for (int i = 0; i < length; i++) {
      if (el[i] == 0.0D) { el[i] = 0.0D;
      } else
        el[i] /= el[i];
    }
    return v;
  }
  
  public Vect cross(Vect u)
  {
    if (length > 3) throw new IllegalArgumentException("Cross product is not defined for dimentions higher than 3");
    if (length != length) { throw new IllegalArgumentException("vectrs have different lengths");
    }
    if (length == 2) {
      return new Vect(0.0D, 0.0D, el[0] * el[1] - el[1] * el[0]);
    }
    Vect w = new Vect(3);
    el[0] = (el[1] * el[2] - el[2] * el[1]);
    el[1] = (el[2] * el[0] - el[0] * el[2]);
    el[2] = (el[0] * el[1] - el[1] * el[0]);
    return w;
  }
  
  public double norm()
  {
    double s = 0.0D;
    for (int i = 0; i < length; i++)
      s += el[i] * el[i];
    return Math.sqrt(s);
  }
  
  public void normalize()
  {
    double normRev = 1.0D / norm();
    timesVoid(normRev);
  }
  
  public Vect normalized()
  {
    double normRev = 1.0D / norm();
    return times(normRev);
  }
  
  public void show()
  {
    int I = length;
    for (int i = 0; i < I; i++)
      System.out.format("%10.5f\n", new Object[] { Double.valueOf(el[i]) });
    System.out.println();
  }
  
  public void length()
  {
    System.out.println(length);
  }
  
  public void hshow()
  {
    int I = length;
    double am = abs().max();
    int nf = (int)Math.log10(am);
    if (nf < 5) {
      for (int i = 0; i < I; i++)
        System.out.format("%15.5f", new Object[] { Double.valueOf(el[i]) });
    } else if (nf < 10) {
      for (int i = 0; i < I; i++)
        System.out.format("%20.5f", new Object[] { Double.valueOf(el[i]) });
    } else
      for (int i = 0; i < I; i++)
        System.out.format("%25.5f", new Object[] { Double.valueOf(el[i]) });
    System.out.println();
  }
  

  public int[] bubble()
  {
    if (length < 1) throw new IllegalArgumentException("Null vector");
    int[] indice = new int[length];
    for (int i = 0; i < length; i++) {
      indice[i] = i;
    }
    if (length == 1) return indice;
    int n = el.length;
    for (int pass = 0; pass < n - 1; pass++) {
      for (int i = 0; i < n - 1 - pass; i++) {
        if (el[i] > el[(i + 1)]) {
          double temp = el[i];el[i] = el[(i + 1)];el[(i + 1)] = temp;
          int tempi = indice[i];indice[i] = indice[(i + 1)];indice[(i + 1)] = tempi;
        }
      }
    }
    

    return indice;
  }
  


  int partition(double[] arr, int left, int right)
  {
    int i = left;int j = right;
    
    double pivot = arr[((left + right) / 2)];
    while (i <= j) {
      while (arr[i] < pivot)
        i++;
      while (arr[j] > pivot)
        j--;
      if (i <= j) {
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        i++;
        j--;
      }
    }
    
    return i;
  }
  
  public void quickSort(double[] arr, int left, int right) {
    int index = partition(arr, left, right);
    if (left < index - 1)
      quickSort(arr, left, index - 1);
    if (index < right)
      quickSort(arr, index, right);
  }
  
  public void quickSort() {
    int index = partition(el, 0, length - 1);
    int left = 0;
    if (left < index - 1)
      quickSort(el, left, index - 1);
    if (index < length - 1) {
      quickSort(el, index, length - 1);
    }
  }
  


  private double trunc(double a, int n) { return Math.floor(a * Math.pow(10.0D, n)) / Math.pow(10.0D, n); }
  
  public void trunc(int n) {
    for (int i = 0; i < length; i++) {
      el[i] = trunc(el[i], n);
    }
  }
}
