package edu.lysenko.catalog.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		try {
			registry = new StandardServiceRegistryBuilder().configure().build();

			MetadataSources sources = new MetadataSources(registry);

			Metadata metadata = sources.getMetadataBuilder().build();

			sessionFactory = metadata.getSessionFactoryBuilder().build();

		} catch (Exception e) {
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
			e.printStackTrace();
		}
		return sessionFactory;
	}
}
