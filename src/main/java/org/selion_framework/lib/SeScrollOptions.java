package org.selion_framework.lib;

public class SeScrollOptions {
    private Behavior behavior;
    private Block block;
    private Inline inline;

    SeScrollOptions() {
        this.behavior = Behavior.instant;
        this.block = Block.center;
        this.inline = Inline.center;
    }

    public SeScrollOptions behavior(Behavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public SeScrollOptions block(Block block) {
        this.block = block;
        return this;
    }

    public SeScrollOptions inline(Inline inline) {
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
