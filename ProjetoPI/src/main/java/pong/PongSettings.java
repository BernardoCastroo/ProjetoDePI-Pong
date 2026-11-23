package pong;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PongSettings implements EntityFactory {

    @Spawns("bola")
    public Entity novaBola(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(TipoEntidade.BOLA)
                .viewWithBBox(new Circle(10, 10, 10, Color.BLACK))
                .with(new CollidableComponent(true)) 
                .with("velocidadeX", 300.0) 
                .with("velocidadeY", 300.0)
                .build();
    }

    @Spawns("raquete")
    public Entity novaRaquete(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(TipoEntidade.RAQUETE)
                .viewWithBBox(new Rectangle(20, 100, Color.BLACK)) // Retângulo 20x100
                .with(new CollidableComponent(true))
                .build();
    }
}