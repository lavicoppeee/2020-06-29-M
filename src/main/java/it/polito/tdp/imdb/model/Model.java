package it.polito.tdp.imdb.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	ImdbDAO dao;
	private Graph<Director,DefaultWeightedEdge> graph;
	List<Director> vertici;
	List<Arco> archi;
	
	
	List<Director> bestCammino;
	Double bestPeso;



	public Model() {
		dao=new ImdbDAO();
	}
	
	
	public void creaGrafo(Integer anno) {

		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici=this.dao.getVertici(anno);
		Graphs.addAllVertices(graph, vertici);
		
		archi=this.dao.getArchi(anno);
		
		for(Arco a: archi) {
			if(this.graph.containsVertex(a.d1) && this.graph.containsVertex(a.getD2())) {
				Graphs.addEdgeWithVertices(graph,a.getD1(), a.getD2(),a.getPeso());
			}
		}
		
	}
	
	public List<Director> getVertici(){
		return vertici;
	}
	
	public List<Integer> getAnno(){
		return dao.getAnno();
	}
	
	//NUMERO VERTICI:

	public int nVertici() {
			return this.graph.vertexSet().size();
		}

	//NUMERO ARCHI:

		public int nArchi() {
			return this.graph.edgeSet().size();
		}
		
		public List<Arco> getConnessi(Director d){
			List<Arco> result =new ArrayList<>();
			List<Director> vicini= Graphs.neighborListOf(graph, d);
			
			for(Director v: vicini) {
				if(!result.contains(v)) {
					Double peso= this.graph.getEdgeWeight(this.graph.getEdge(d, v));
					Arco a= new Arco(d,v,peso);
					result.add(a);
				}
			}
			
			Collections.sort(result);
			
			return result;
		}
		
		
		public void trovaDirettori(int c, Director d) {
			bestCammino=null;
			bestPeso=0.0;
			List<Director> parziale=new ArrayList<>();
			parziale.add(d);
			ricorsione(parziale,-1,c);
		}
		
		
	private void ricorsione(List<Director> parziale, int p, int c) {

		Director last = parziale.get(parziale.size() - 1);

		List<Director> vicini = Graphs.neighborListOf(graph, last);

		for (Director v : vicini) {
			if (!parziale.contains(v) && p == -1) {
				parziale.add(v);
				ricorsione(parziale, -1, c); // backtracking

			} else {
				if (!parziale.contains(v) && this.graph.getEdgeWeight(this.graph.getEdge(last, v)) <= c) {
					parziale.add(v);
					int pNew = (int) this.graph.getEdgeWeight(this.graph.getEdge(last, v));
					ricorsione(parziale, pNew, c);
					parziale.remove(v);
				}
			}

		}

		Double peso = calcolaPeso(parziale);
		if (peso > bestPeso) {
			this.bestPeso = peso;
			this.bestCammino = new ArrayList<>(parziale);
			return;
		}

	}

	private double calcolaPeso(List<Director> parziale) {
         double peso=0.0;
		
		for(int i=1; i<parziale.size();i++) {
			Double pNew=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1),parziale.get(i)));
			peso+=pNew;
		}
		
		return peso;
	}
			
		


		public List<Director> getBestCammino() {
			return bestCammino;
		}


		public Double getBestPeso() {
			return bestPeso;
		}
		
		
		
		
		
}
