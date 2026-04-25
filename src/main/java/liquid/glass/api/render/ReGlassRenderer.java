package liquid.glass.api.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import liquid.glass.client.gui.LiquidGlassGuiElementRenderState;
import liquid.glass.mixin.accessor.DrawContextAccessor;

public final class ReGlassRenderer {
    private ReGlassRenderer() {}

    public static Builder create(DrawContext context) {
        return new Builder(context);
    }

    public static class Builder {
        private final DrawContext context;
        private int x, y, width, height;
        private float cornerRadius = -1f;
        @Nullable private Text text = null;
        private ReGlassStyle style = new ReGlassStyle();
        private float hoverAmount = 0f;
        private float focusAmount = 0f;

        private Builder(DrawContext context) {
            this.context = context;
        }

        public Builder fromWidget(ClickableWidget widget) {
            this.position(widget.getX(), widget.getY());
            this.size(widget.getWidth(), widget.getHeight());
            this.text(widget.getMessage());
            return this;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public Builder cornerRadius(float radius) {
            this.cornerRadius = radius;
            return this;
        }

        public Builder text(Text text) {
            this.text = text;
            return this;
        }

        public Builder style(ReGlassStyle style) {
            this.style = style;
            return this;
        }

        public Builder hover(float amount) {
            if (Float.isNaN(amount)) amount = 0f;
            this.hoverAmount = Math.max(0f, Math.min(1f, amount));
            return this;
        }

        public Builder focus(float amount) {
            if (Float.isNaN(amount)) amount = 0f;
            this.focusAmount = Math.max(0f, Math.min(1f, amount));
            return this;
        }

        public Builder selected(float amount) {
            return this.focus(amount);
        }

        public void render() {
            float finalCornerRadius = this.cornerRadius < 0 ? 0.5f * Math.min(this.width, this.height) : this.cornerRadius;
            Matrix3x2f pose = new Matrix3x2f(context.getMatrices());
            ScreenRect scissorRect = ((DrawContextAccessor) context).getScissorStack().peekLast();
            context.state.addSpecialElement(new LiquidGlassGuiElementRenderState(
                    this.x, this.y, this.x + this.width, this.y + this.height,
                    finalCornerRadius, this.text, this.style, pose, scissorRect,
                    this.hoverAmount, this.focusAmount
            ));
        }
    }
}
