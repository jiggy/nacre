//package com.nacre.servlet;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.tiles.TilesApplicationContext;
//import org.apache.tiles.TilesContainer;
//import org.apache.tiles.context.TilesRequestContextFactory;
//import org.apache.tiles.definition.DefinitionsFactoryException;
//import org.apache.tiles.factory.AbstractTilesContainerFactory;
//import org.apache.tiles.factory.BasicTilesContainerFactory;
//import org.apache.tiles.impl.BasicTilesContainer;
//import org.apache.tiles.servlet.context.ServletTilesRequestContextFactory;
//import org.apache.tiles.startup.AbstractTilesInitializer;
//import org.apache.tiles.startup.TilesInitializer;
//import org.apache.tiles.web.startup.AbstractTilesInitializerServlet;
//
//public class NacresTilesInitializerServlet extends
//		AbstractTilesInitializerServlet {
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected TilesInitializer createTilesInitializer() {
//		System.out.println("initializing tiles!");
//		return new NacreTilesInitializer();
//	}
//	
//	public static class NacreTilesInitializer extends AbstractTilesInitializer {
//		@Override
//		protected AbstractTilesContainerFactory createContainerFactory(
//				TilesApplicationContext arg0) {
//			return new NacreTilesContainerFactory();
//		}		
//	}
//	
//	public static class NacreTilesContainerFactory extends BasicTilesContainerFactory {
//		@Override
//		public TilesContainer createContainer(TilesApplicationContext arg0) {
//			return new BasicTilesContainer();
//		}
//
//		@Override
//		protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
//				TilesRequestContextFactory arg1) {
//	        List<URL> urls = new ArrayList<URL>();
//	        try {
//	            urls.add(applicationContext.getResource("/WEB-INF/tiles-defs.xml"));
//	        } catch (IOException e) {
//	            throw new DefinitionsFactoryException("Cannot load definition URLs", e);
//	        }
//	        System.out.println("urls: " + urls);
//	        return urls;
//		}	
//	}
//	
//	public static class NacreTilesContainer extends BasicTilesContainer {
//
//		@Override
//		protected TilesRequestContextFactory getRequestContextFactory() {
//			return new ServletTilesRequestContextFactory();
//		}
//		
//	}
//}
