package com.zzy.elfinder.web.vo;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zzy.elfinder.utils.FileUtil;
import com.zzy.elfinder.utils.HashUtil;
import com.zzy.elfinder.utils.MimeUtil;
import com.zzy.elfinder.web.Config;

/*{
    "name"   : "Images",             // (String) name of file/dir. Required
    "hash"   : "l0_SW1hZ2Vz",        // (String) hash of current file/dir path, first symbol must be letter, symbols before _underline_ - volume id, Required. 
    "phash"  : "l0_Lw",              // (String) hash of parent directory. Required except roots dirs.
    "mime"   : "directory",          // (String) mime type. Required.
    "ts"     : 1334163643,           // (Number) file modification time in unix timestamp. Required.
    "date"   : "30 Jan 2010 14:25",  // (String) last modification time (mime). Depricated but yet supported. Use ts instead.
    "size"   : 12345,                // (Number) file size in bytes
    "dirs"   : 1,                    // (Number) Only for directories. Marks if directory has child directories inside it. 0 (or not set) - no, 1 - yes. Do not need to calculate amount.
    "read"   : 1,                    // (Number) is readable
    "write"  : 1,                    // (Number) is writable
    "locked" : 0,                    // (Number) is file locked. If locked that object cannot be deleted and renamed
    "tmb"    : 'bac0d45b625f8d4633435ffbd52ca495.png' // (String) Only for images. Thumbnail file name, if file do not have thumbnail yet, but it can be generated than it must have value "1"
    "alias"  : "files/images",       // (String) For symlinks only. Symlink target path.
    "thash"  : "l1_c2NhbnMy",        // (String) For symlinks only. Symlink target hash.
    "dim"    : "640x480"             // (String) For images - file dimensions. Optionally.
    "volumeid" : "l1_"               // (String) Volume id. For root dir only.
}*/
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class ElFile {
	private String name;
	private String hash;
	private String phash;
	private String mime;
	private Long ts;
	private Long size;
	private Integer dirs;
	private Integer read;
	private Integer write;
	private Integer locked;
	private Object tmb;
	private String alias;
	private String thash;
	private String dim;
	private String volumeid;
	private String date;
	private String url;//only for search
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPhash() {
		return phash;
	}
	public void setPhash(String phash) {
		this.phash = phash;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Integer getDirs() {
		return dirs;
	}
	public void setDirs(Integer dirs) {
		this.dirs = dirs;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public Integer getWrite() {
		return write;
	}
	public void setWrite(Integer write) {
		this.write = write;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Object getTmb() {
		return tmb;
	}
	public void setTmb(Object tmb) {
		this.tmb = tmb;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getThash() {
		return thash;
	}
	public void setThash(String thash) {
		this.thash = thash;
	}
	public String getDim() {
		return dim;
	}
	public void setDim(String dim) {
		this.dim = dim;
	}
	public String getVolumeid() {
		return volumeid;
	}
	public void setVolumeid(String volumeid) {
		this.volumeid = volumeid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static ElFile getInstance(String hash) throws IOException{
		String[] strs=HashUtil.decode(hash);
//		File file=new File(ElfinderService.ROOT_PATH,strs[1]);
//		ElFile cwd=new ElFile();
//		if(FileUtil.hasSubdirs(file))
//			cwd.setDirs(1);
//		cwd.setHash(hash);
//		cwd.setMime(MimeUtil.getMime(file));
//		cwd.setName(FileUtil.getFileName(file));
//		
//		cwd.setRead(1);
//		if(file.isDirectory())
//			cwd.setSize(0L);
//		else
//			cwd.setSize(file.length());
////		cwd.setTmb(tmb);
//		cwd.setTs(FileUtil.getTs(file));
//		String phash=HashUtil.phash(hash);
//		if(phash!=null){
//			cwd.setPhash(phash);
//		}else{//根目录
//			cwd.setVolumeid(strs[0]);
//			cwd.setLocked(1);
//		}
//		cwd.setWrite(1);
//		return cwd;
		return getInstance(strs[0],strs[1],hash);
	}
	public static ElFile getInstance(String volumnid,String path) throws IOException{
		String hash=HashUtil.encode(volumnid, path);
		return getInstance(volumnid,path,hash);
	}
	public static ElFile getInstance(String volumnid,String path,String hash) throws IOException{
		File file=new File(Config.ROOT_PATH,path);
		ElFile cwd=new ElFile();
		if(FileUtil.hasSubdirs(file))
			cwd.setDirs(1);
		cwd.setHash(hash);
		cwd.setMime(MimeUtil.getMime(file));
		if(cwd.getMime().startsWith("image/")){
			cwd.setTmb(hash);
		}
		cwd.setName(FileUtil.getFileName(file));
		
		cwd.setRead(1);
		if(file.isDirectory())
			cwd.setSize(0L);
		else
			cwd.setSize(file.length());
//		cwd.setTmb(tmb);
		cwd.setTs(FileUtil.getTs(file));
		cwd.setDate((new Date(file.lastModified())).toGMTString());//没这个属性preview不起效
		String phash=HashUtil.phash(hash);
		if(phash!=null){
			cwd.setPhash(phash);
		}else{//根目录
			cwd.setVolumeid(volumnid);
			cwd.setLocked(1);
		}
		cwd.setWrite(1);
		return cwd;
	}
	public static ElFile getSearchInstance(String volumnid,String path) throws IOException{
		String hash=HashUtil.encode(volumnid, path);
		ElFile ef= getInstance(volumnid,path,hash);
		ef.setUrl(Config.URL+path);
		return ef;
	}
	public static ElFile getNewTmbInstance(String hash) throws IOException{
		ElFile ef=getInstance(hash);
		ef.setTmb(Integer.valueOf(1));//告诉页面tmb已经发生变化
		return ef;
	}
}
