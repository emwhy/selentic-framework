package org.selion_framework.lib;

public class SnScrollOptions {
    private Behavior behavior;
    private Block block;
    private Inline inline;

    SnScrollOptions() {
        this.behavior = Behavior.instant;
        this.block = Block.center;
        this.inline = Inline.center;
    }

    public SnScrollOptions behavior(Behavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public SnScrollOptions block(Block block) {
        this.block = block;
        return this;
    }

    public SnScrollOptions inline(Inline inline) {
        this.inline = inline;
        return this;
    }

    @Override
    public String toString() {
        return "{behavior: '%s', block: '%s', inline: '%s'}".formatted(this.behavior, this.block, this.inline);
    }

    public enum Behavior {
        smooth,
        instant,
        auto
    }

    public enum Block {
        start,
        center,
        end,
        nearest
    }

    public enum Inline {
        start,
        center,
        end,
        nearest
    }
}
