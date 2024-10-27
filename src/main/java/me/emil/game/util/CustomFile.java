package me.emil.game.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
@author Tijn van Heesewijk
 */

public class CustomFile extends File
{
    public CustomFile(String filePath)
    {
        super(filePath);
    }
    
    public CustomFile(String parent, String child)
    {
        super(parent, child);
        checkSupported();
    }
    
    public CustomFile(File parent, String child)
    {
        super(parent, child);
        checkSupported();
    }
    
    public CustomFile(URI uri)
    {
        super(uri);
        checkSupported();
    }
    
    public CustomFile(File file)
    {
        super(file.getPath());
        checkSupported();
    }
    
    public CustomFile(CustomFile file)
    {
        super(file.getPath());
    }
    
    @Override
    public final boolean createNewFile()
    {
        if(this.exists())
            return false;
        
        if(isDirectory())
            mkdirs();
        else
            getParentFile().mkdirs();
        
        try
        {
            return super.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public final boolean delete()
    {
        return this.isDirectory() ? this.removeDirectory(this) : super.delete();
    }
    
    public final boolean copyContent(CustomFile destination)
    {
        return copyContent(destination, false);
    }
    
    public final boolean copyContent(CustomFile destination, boolean replaceExisting)
    {
        if(destination.isDirectory())
            throw new IllegalArgumentException("The file is a directory!");
        
        destination.createNewFile();
        
        if(!replaceExisting && destination.length() != 0)
            return false;
        
        try
        {
            Files.copy(toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> getContent()
    {
        List<String> content = new ArrayList<>();
        
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(this));
            
            String nextLine;
            while((nextLine = reader.readLine()) != null)
                content.add(nextLine);
            
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return content;
    }
    
    public void setContent(List<String> lines)
    {
        setContent(lines, false);
    }
    
    public void setContent(List<String> lines, boolean append)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(this, append);
            
            for(String line : lines)
                fileWriter.write(line + "\n");
            
            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void downloadContent(String url)
    {
        downloadContent(url, false);
    }
    
    public void downloadContent(String url, boolean append)
    {
        try
        {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(this, append);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.flush();
            fileOutputStream.close();
            
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }
    
    public final List<? extends CustomFile> getListedFiles()
    {
        return getListedFiles(getClass(), getSupportedTypes().toArray(new String[0]));
    }
    
    public final List<? extends CustomFile> getListedFiles(String... extensions)
    {
        return getListedFiles(getClass(), extensions);
    }
    
    public final <T extends CustomFile> List<T> getListedFiles(Class<T> clazz)
    {
        return getListedFiles(clazz, getSupportedTypes().toArray(new String[0]));
    }
    
    public final <T extends CustomFile> List<T> getListedFiles(Class<T> clazz, String... extensions)
    {
        List<String> extensionsList = Arrays.asList(extensions);
        List<T> files = new ArrayList<>();
        Constructor<T> constructor;
        
        try
        {
            constructor = clazz.getConstructor(File.class);
        }
        catch(NoSuchMethodException e)
        {
            throw new IllegalArgumentException(clazz + " does not support getListedFiles()!", e);
        }
        
        if(isDirectory())
        {
            File[] listed = listFiles();
            
            if(listed == null || listed.length == 0)
                return files;
            
            for(File file : listed)
            {
                try
                {
                    String[] fileName = file.getName().split("\\.");
                    
                    if(!extensionsList.contains(fileName[fileName.length - 1]))
                        continue;
                    
                    files.add(constructor.newInstance(file));
                }
                catch(InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch(InvocationTargetException | IllegalArgumentException ignored)
                {
                }
            }
        }
        
        return files;
    }
    
    protected List<String> getSupportedTypes()
    {
        return new ArrayList<>();
    }
    
    private boolean removeDirectory(File path)
    {
        boolean succeed = true;
        
        if(path.exists())
        {
            File[] files = path.listFiles();
            
            if(files == null)
                return false;
            
            for(File file : files)
            {
                if(file.isDirectory())
                    this.removeDirectory(file);
                else if(!file.delete())
                    succeed = false;
            }
        }
        
        return succeed;
    }
    
    private void checkSupported()
    {
        if(isDirectory() || getName().split("\\.").length < 2)
            return;
        
        String fileType = getName().split("\\.")[1];
        
        if(!getSupportedTypes().isEmpty() && !getSupportedTypes().contains(fileType))
            throw new IllegalArgumentException(getClass() + " does not support ." + fileType + " files!");
    }
}
