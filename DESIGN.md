# Design System Specification: Editorial Fluidity

## 1. Overview & Creative North Star: "The Ethereal Curator"
The Creative North Star for this design system is **"The Ethereal Curator."** We are moving away from the rigid, boxy constraints of traditional SaaS dashboards toward a layout that feels like a high-end digital gallery. 

This system rejects the "template" look. It favors intentional asymmetry, generous white space, and a "liquid" structural philosophy. By utilizing `ROUND_FULL` (9999px) for primary containers and buttons, we evoke the organic smoothness of water droplets or polished glass. The interface should feel like it is floating on a soft, atmospheric haze rather than being locked to a grid.

---

## 2. Colors & Surface Architecture

### The Palette
The color logic is built on "Low-Contrast Sophistication." We avoid the harshness of pure `#FFFFFF` and `#000000` to create a premium, easy-on-the-eyes experience.

*   **Foundation:** `surface` (#F7F9FB) – A soft, breathable base.
*   **Ink:** `on_surface` (#191C1E) – A deep, navy-tinted charcoal that feels more expensive than flat black.
*   **The Pulse:** `primary` (#003EC7) and `primary_container` (#0052FF) – Used sparingly for high-intent actions.

### The "No-Line" Rule
**Explicit Instruction:** Designers are prohibited from using 1px solid borders for sectioning. Boundaries must be defined solely through:
1.  **Background Color Shifts:** Placing a `surface_container_low` section against the `surface` background.
2.  **Tonal Transitions:** Using depth and elevation rather than lines. 

### The "Glass & Gradient" Rule
To achieve the "Liquid" aesthetic, floating elements (modals, navigation bars, dropdowns) must utilize **Glassmorphism**:
*   **Fill:** `surface_container_lowest` at 70%–85% opacity.
*   **Effect:** `backdrop-blur` (12px to 20px).
*   **Signature Texture:** Use a subtle linear gradient on primary CTAs (from `primary_container` to `primary`) to provide a "convex" glass feel.

---

## 3. Typography: The Manrope Editorial
We use **Manrope** for its geometric yet modern humanist qualities. The hierarchy is designed to feel like a high-fashion magazine layout.

*   **Display (lg/md):** Use for "Hero" moments. Tighten letter-spacing (-2%) to create an authoritative, "locked-in" look.
*   **Headlines:** Use `headline-lg` for section starts. Ensure significant bottom margin to let the type breathe.
*   **Body (lg/md):** Set at `on_surface_variant` (#434656) for long-form reading to reduce visual weight, reserving `on_surface` (#191C1E) for titles.
*   **Labels:** Use `label-md` in All Caps with +5% letter-spacing for a sophisticated, technical feel.

---

## 4. Elevation & Depth: Tonal Layering
Traditional shadows are too heavy for this system. We use **Tonal Layering** to create a sense of physical stacking.

### The Layering Principle
Depth is achieved by "stacking" surface tiers:
*   **Level 0 (Base):** `surface` (#F7F9FB).
*   **Level 1 (Sections):** `surface_container_low` (#F2F4F6).
*   **Level 2 (Cards/Content):** `surface_container_lowest` (#FFFFFF).

### Ambient Shadows
If a floating effect is required (e.g., a "Liquid" Hover state):
*   **Blur:** 32px – 64px.
*   **Opacity:** 4% – 6%.
*   **Tint:** Use a shadow color derived from `on_surface` (#191C1E) rather than pure black to keep the atmosphere airy.

### The "Ghost Border" Fallback
If a container lacks sufficient contrast against its background, use a **Ghost Border**: `outline_variant` at 15% opacity. Never use a 100% opaque border.

---

## 5. Components

### Buttons & Chips
*   **Shape:** Always `ROUND_FULL`.
*   **Primary:** `primary_container` (#0052FF) with `on_primary` text. No border.
*   **Secondary:** `surface_container_high` background. Feels like an integrated part of the UI.
*   **Interaction:** On hover, apply a subtle `surface_tint` glow rather than a dark overlay.

### Input Fields
*   **Visual Style:** Subtle `surface_container_highest` background with no border. 
*   **Focus State:** A 2px "aura" (outer glow) using `primary_fixed_dim` (#B7C4FF) rather than a hard stroke.

### Cards & Lists
*   **The Divider Ban:** Strictly forbid the use of horizontal rules (`<hr>`). 
*   **Separation:** Use 24px–48px of vertical white space or a subtle shift to `surface_container_low` to separate items.
*   **Corner Radius:** Cards use `xl` (3rem) or `lg` (2rem) for a friendly, organic feel.

### Navigation "Liquid" Bar
*   A floating pill-shaped dock centered at the bottom or top of the screen.
*   **Style:** Glassmorphic (`surface_container_lowest` @ 80% opacity) + `backdrop-blur`.
*   **Radius:** `ROUND_FULL`.

---

## 6. Do’s and Don’ts

### Do:
*   **Do** use asymmetrical margins (e.g., a wider left margin than right) to create editorial interest.
*   **Do** overlap elements (e.g., a glass chip partially overlapping an image) to show depth.
*   **Do** prioritize "negative space" as a functional element of the design.

### Don't:
*   **Don't** use pure black text. It breaks the "Liquid Glass" immersion.
*   **Don't** use sharp corners. Every primitive must have at least an `sm` (0.5rem) radius, but `full` is preferred.
*   **Don't** use "Drop Shadows" from a 90-degree angle. Use centered, diffused ambient glows to mimic natural light.
*   **Don't** crowd the interface. If you think it needs more features, it probably needs more padding instead.