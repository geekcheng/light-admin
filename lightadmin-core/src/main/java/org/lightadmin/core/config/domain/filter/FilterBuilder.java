package org.lightadmin.core.config.domain.filter;

import org.lightadmin.core.config.domain.support.Builder;
import org.lightadmin.core.config.domain.renderer.Renderer;
public interface FilterBuilder extends Builder<Filters> {

	FilterBuilder field( final String fieldName );

	FilterBuilder renderer( final Renderer renderer );

	Filters build();
}