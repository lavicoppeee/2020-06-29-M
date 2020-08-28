package it.polito.tdp.imdb.model;

public class Arco implements Comparable<Arco>{

	Director d1;
	Director d2;
	Double peso;
	
	public Arco(Director d1, Director d2, Double peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public Director getD2() {
		return d2;
	}
	public void setD2(Director d2) {
		this.d2 = d2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return -this.peso.compareTo(o.getPeso());
	}
	@Override
	public String toString() {
		return d2.getId()+" - " +d2.getFirstName()+"  "+d2.getLastName()+" - "+ "# attori condivisi"+" "+ peso;
	}
	
	
	
	
	
}
