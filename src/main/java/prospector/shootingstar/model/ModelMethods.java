package prospector.shootingstar.model;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Map;

public class ModelMethods {
	public static void registerItemModel(Item item) {
		setMRL(item, 0, item.getRegistryName(), "inventory");
	}

	public static void registerItemModel(Item item, int meta) {
		setMRL(item, meta, item.getRegistryName(), "inventory");
	}

	public static void registerItemModel(Item item, String fileName, String path) {
		ResourceLocation loc = new ResourceLocation(item.getRegistryName().getResourceDomain(), path + "/" + item.getRegistryName().getResourcePath());
		setMRL(item, 0, loc, "inventory");
	}

	public static void registerItemModel(Item item, int meta, String path, String invVariant) {
		String slash = "";
		if (!path.isEmpty())
			slash = "/";
		ResourceLocation loc = new ResourceLocation(item.getRegistryName().getResourceDomain(), path + slash + item.getRegistryName().getResourcePath());
		setMRL(item, meta, loc, invVariant);
	}

	public static void registerItemModel(Item item, int meta, String fileName, String path, String invVariant) {
		String slash = "";
		if (!path.isEmpty())
			slash = "/";
		ResourceLocation loc = new ResourceLocation(item.getRegistryName().getResourceDomain(), path + slash + fileName);
		setMRL(item, meta, loc, invVariant);
	}

	public static void registerBlockState(Item item, int meta, String path, String property, String variant) {
		registerBlockState(item, meta, path, property + "=" + variant);
	}

	public static void registerBlockState(Item item, int meta, String path, String variant) {
		ResourceLocation loc = new ResourceLocation(item.getRegistryName().getResourceDomain(), path + "/" + item.getRegistryName().getResourcePath());
		setMRL(item, meta, loc, variant);
	}

	public static void setMRL(Item item, int meta, ResourceLocation resourceLocation, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resourceLocation, variant));
	}

	public static void setCustomStateMapper(Block block, IStateMapper mapper) {
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	public static void setBlockStateMapper(Block block, IProperty... ignoredProperties) {
		setBlockStateMapper(block, block.getRegistryName().getResourcePath(), ignoredProperties);
	}

	public static void setBlockStateMapper(Block block, String blockstatePath, IProperty... ignoredProperties) {
		setBlockStateMapper(block, block.getRegistryName().getResourcePath(), blockstatePath, ignoredProperties);
	}

	public static void setBlockStateMapper(Block block, String fileName, String path, IProperty... ignoredProperties) {
		final String slash = !path.isEmpty() ? "/" : "";
		ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());
				for (IProperty<?> iproperty : ignoredProperties) {
					map.remove(iproperty);
				}
				return new ModelResourceLocation(new ResourceLocation(block.getRegistryName().getResourceDomain(), path + slash + fileName), this.getPropertyString(map));
			}
		});
	}
}
